package com.froud.fraudservice.repository;

import com.froud.fraudservice.entity.DepoDataDebt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
               0                                                                        as reminderPassive,
               0                                                                        as taxBase,
               0                                                                        as vatSumTimeDiff,
               sum(case debtBeforeMonth.vat_amount > 0
                       when false
                           then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                       else debtBeforeMonth.amount_without_vat -
                            coalesce((5 * (select sum(p.amount)
                                           from depo_data.pay p
                                           where p.bill_id = debtBeforeMonth.bill_id
                                             and p.status = 'PAID')), 0) / 6 end)       as decreaseSumTimeDiff,
               null                                                                     as sumLoss,
               20.00                                                                    as ndsPercent,
               0                                                                        as finResult,
               0                                                                        as addCapital,
               sum(case debtBeforeMonth.vat_amount > 0
                       when false
                           then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                       else debtBeforeMonth.amount_without_vat -
                            coalesce((5 * (select sum(p.amount)
                                           from depo_data.pay p
                                           where p.bill_id = debtBeforeMonth.bill_id
                                             and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat,
               0                                                                        as addCapital2,
               null                                                                     as sumOna,
               sum(case debtBeforeMonth.vat_amount > 0
                       when false
                           then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
                       else debtBeforeMonth.amount_without_vat -
                            coalesce((5 * (select sum(p.amount)
                                           from depo_data.pay p
                                           where p.bill_id = debtBeforeMonth.bill_id
                                             and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat2,
               0                                                                        as addCapital3,
               null                                                                     as sumTakenLoss,
               0                                                                        as saldo61701,
               null                                                                     as saldo61703,
               null                                                                     as saldo10609
        from depo_data.debt debt
                 left join depo_data.debt debtBeforeMonth
                           on debt.id = debtBeforeMonth.id
                 left join depo_data.depositor depositor on debt.debt_depositor = depositor.id
         where debt.status in ('REGISTERED', 'HALF_PAID')
          and depositor.personal_account_number is not null
          and debt.bill_formed_date between :dateFrom and :dateTo
        group by debt.depositor_type, balanceAccountNumber, personalAccountNumber
        order by debt.depositor_type;
        """, nativeQuery = true)
    List<DebtProjection> getDebtInformation(@Param("dateFrom") LocalDate dateFrom,
                                            @Param("dateTo") LocalDate dateTo);
}
