package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.DepoDataDebt;
import com.froud.fraudservice.entity.DepoDataResultInner;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface DebtRepository extends JpaRepository<DepoDataDebt, Integer> {
    @Query(value = """
            select debt.depositor_type,
                    concat(left(depositor.personal_account_number, 5), '.',
                          substr(depositor.personal_account_number, 14, 2))                 as balanceAccountNumber,
                   depositor.personal_account_number                                        as personalAccountNumber,
                   'default'                                                                as nameAccount,
                   sum(case debt.vat_amount > 0
                           when false
                               then case debt.remainder > 0 when true then debt.remainder else 0 end
                           else debt.amount_without_vat -
                                coalesce((5 * (select sum(p.amount)
                                               from depo_data.pay p
                                               where p.bill_id = debt.bill_id
                                                 and p.status = 'PAID')), 0) / 6 end)       as reminderActive,
                   100.00                                                                   as reminderPassive,
                   100.00                                                                   as taxBase,
                   100.00                                                                   as vatSumTimeDiff,
                   sum(case debtBeforeMonth.vat_amount > 0
                           when false
                               then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                           else debtBeforeMonth.amount_without_vat -
                                coalesce((5 * (select sum(p.amount)
                                               from depo_data.pay p
                                               where p.bill_id = debtBeforeMonth.bill_id
                                                 and p.status = 'PAID')), 0) / 6 end)       as decreaseSumTimeDiff,
                   '100'                                                                    as sumLoss,
                   20.00                                                                    as ndsPercent,
                   100.00                                                                   as finResult,
                   100.00                                                                   as addCapital,
                   sum(case debtBeforeMonth.vat_amount > 0
                           when false
                               then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                           else debtBeforeMonth.amount_without_vat -
                                coalesce((5 * (select sum(p.amount)
                                               from depo_data.pay p
                                               where p.bill_id = debtBeforeMonth.bill_id
                                                 and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat,
                   100.00                                                                   as addCapital2,
                   '100'                                                                    as sumOna,
                   sum(case debtBeforeMonth.vat_amount > 0
                           when false
                               then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                           else debtBeforeMonth.amount_without_vat -
                                coalesce((5 * (select sum(p.amount)
                                               from depo_data.pay p
                                               where p.bill_id = debtBeforeMonth.bill_id
                                                 and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat2,
                   100.00                                                                   as addCapital3,
                   '100'                                                                    as sumTakenLoss,
                   100.00                                                                   as saldo61701,
                   '100'                                                                    as saldo61703,
                   '100'                                                                    as saldo10609
            from depo_data.debt debt
                     left join depo_data.debt debtBeforeMonth
                               on debt.id = debtBeforeMonth.id
                     left join depo_data.depositor depositor on debt.debt_depositor = depositor.id
            where debt.status in ('REGISTERED', 'HALF_PAID')
              and depositor.personal_account_number is not null
              and debt.bill_formed_date between :dateFrom and :dateTo
              group by personalAccountNumber, debt.depositor_type
              order by debt.depositor_type;
                        """, nativeQuery = true)
    List<Tuple> getDebtInformation(@Param("dateFrom") LocalDate dateFrom,
                                   @Param("dateTo") LocalDate dateTo,
                                   Pageable pageable);

    @Transactional(readOnly = true)
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    default List<DepoDataResultInner> getDebtInformation(final LocalDate startDate, final LocalDate endDate, final Integer pageSize) {
        var pageRequest = PageRequest.of(0, pageSize);
        List<DepoDataResultInner> result = new ArrayList<>();
        List<DepoDataResultInner> debtInformation;
        do {
            debtInformation = this.getDebtInformation(startDate, endDate, pageRequest)
                .stream()
                .map(t -> DepoDataResultInner.builder()
                    .depositorType(t.get("depositor_type", String.class))
                    .balanceAccountNumber(t.get("balanceAccountNumber", String.class))
                    .personalAccountNumber(t.get("personalAccountNumber", String.class))
                    .reminderActive(t.get("reminderActive", BigDecimal.class))
                    .reminderPassive(t.get("reminderPassive", BigDecimal.class))
                    .taxBase(t.get("taxBase", BigDecimal.class))
                    .vatSumTimeDiff(t.get("vatSumTimeDiff", BigDecimal.class))
                    .decreaseSumTimeDiff(t.get("decreaseSumTimeDiff", BigDecimal.class))
                    .sumLoss(new BigDecimal(t.get("sumLoss", String.class)))
                    .ndsPercent(t.get("ndsPercent", BigDecimal.class))
                    .finResult(t.get("finResult", BigDecimal.class))
                    .addCapital(t.get("addCapital", BigDecimal.class))
                    .finResultVat(t.get("finResultVat", BigDecimal.class))
                    .addCapital2(t.get("addCapital2", BigDecimal.class))
                    .sumOna(new BigDecimal(t.get("sumOna", String.class)))
                    .finResultVat2(t.get("finResultVat2", BigDecimal.class))
                    .addCapital3(t.get("addCapital3", BigDecimal.class))
                    .sumTakenLoss(new BigDecimal(t.get("sumTakenLoss", String.class)))
                    .saldo61701(t.get("saldo61701", BigDecimal.class))
                    .saldo61703(new BigDecimal(t.get("saldo61703", String.class)))
                    .saldo10609(new BigDecimal(t.get("saldo10609", String.class)))
                    .build())
                .toList();
            result.addAll(debtInformation);
            pageRequest = pageRequest.next();
        } while (!debtInformation.isEmpty());

        return result;
    }
}
