-----------------------------------------------------------
-- ������� ��� ������
-----------------------------------------------------------
CREATE TABLE depo_data.debt
(
    id                 SERIAL PRIMARY KEY,
    bill_id            INT,          -- ������� ����, ����������� �� debt.id
    depositor_type     VARCHAR(255), -- ��������� ������ �� �������������
    end_service_period DATE,
    status             VARCHAR(50),  -- ��������� ������ �� �������������
    bill_formed_date   DATE,
    amount_without_vat DECIMAL(18, 2),
    vat_amount         DECIMAL(18, 2),
    remainder          DECIMAL(18, 2),
    debt_depositor     INT           -- ������� ����, ����������� �� depositor.id
);

-- ������� ��� ���������
CREATE TABLE depo_data.depositor
(
    id                      SERIAL PRIMARY KEY,
    personal_account_number VARCHAR(20) UNIQUE
);

-- ������� ��� �������
CREATE TABLE depo_data.pay
(
    id      SERIAL PRIMARY KEY,
    bill_id INT,        -- ������� ����, ����������� �� debt.id
    amount  DECIMAL(18, 2),
    status  VARCHAR(50) -- ��������� ������ �� �������������
);


-----------------------------------------------------------
-- �������� ������ ��� ������� debt
-----------------------------------------------------------
INSERT INTO depo_data.debt (bill_id, depositor_type, end_service_period, status, bill_formed_date, amount_without_vat, vat_amount, remainder, debt_depositor)
VALUES (1, 'Individual', '2023-12-31', 'Unpaid', '2023-01-15', 1000.00, 200.00, 1200.00, 1),
       (2, 'Corporate', '2023-12-31', 'REGISTERED', '2023-02-20', 5000.00, 1000.00, 4000.00, 2),
       (3, 'Individual', '2023-12-31', 'REGISTERED', '2023-03-10', 3000.00, 600.00, 2400.00, 1),
       (4, 'Individual', '2023-12-31', 'REGISTERED', '2023-01-15', 1000.00, 200.00, 1200.00, 1),
       (5, 'Corporate', '2023-12-31', 'REGISTERED', '2023-02-20', 5000.00, 1000.00, 4000.00, 2),
       (6, 'Individual', '2023-12-31', 'REGISTERED', '2023-03-10', 3000.00, 600.00, 2400.00, 1);

-- �������� ������ ��� ������� depositor
INSERT INTO depo_data.depositor (personal_account_number)
VALUES ('ACC123456'),
       ('ACC789012'),
       ('ACC345678');

-- �������� ������ ��� ������� pay
INSERT INTO depo_data.pay (bill_id, amount, status)
VALUES (1, 500.00, 'Processed'),
       (2, 2000.00, 'Pending'),
       (3, 1000.00, 'Paid'),
       (4, 500.00, 'Processed'),
       (5, 2000.00, 'Pending'),
       (6, 1000.00, 'Paid');

-----------------------------------------------------------
-- GENERATE DATA
-----------------------------------------------------------

insert into depo_data.depositor (personal_account_number)
select left(md5(random()::text), 9)
from generate_series(1, 10000) s(i);

INSERT INTO depo_data.debt (bill_id, depositor_type, end_service_period, status,
                            bill_formed_date, amount_without_vat, vat_amount, remainder, debt_depositor)
select i::int,
       'Individual'::text,
       '2022-12-31'::date,
       'REGISTERED'::text,
       '2023-03-10'::date,
       1000.00,
       200.00,
       1200.00,
       i::int
from generate_series(1, 60000) s(i);


-----------------------------------------------------------
-- ��� SELECT ������
-----------------------------------------------------------
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
       100                                                                      as reminderPassive,
       100                                                                      as taxBase,
       100                                                                      as vatSumTimeDiff,
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
       100                                                                      as finResult,
       100                                                                      as addCapital,
       sum(case debtBeforeMonth.vat_amount > 0
               when false
                   then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
               else debtBeforeMonth.amount_without_vat -
                    coalesce((5 * (select sum(p.amount)
                                   from depo_data.pay p
                                   where p.bill_id = debtBeforeMonth.bill_id
                                     and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat,
       100                                                                      as addCapital2,
       '100'                                                                    as sumOna,
       sum(case debtBeforeMonth.vat_amount > 0
               when false
                   then case debtBeforeMonth.remainder > 0 when true then debtBeforeMonth.remainder else 0 end
               else debtBeforeMonth.amount_without_vat -
                    coalesce((5 * (select sum(p.amount)
                                   from depo_data.pay p
                                   where p.bill_id = debtBeforeMonth.bill_id
                                     and p.status = 'PAID')), 0) / 6 end) * 0.2 as finResultVat2,
       100                                                                      as addCapital3,
       '100'                                                                    as sumTakenLoss,
       100                                                                      as saldo61701,
       '100'                                                                    as saldo61703,
       '100'                                                                    as saldo10609
from depo_data.debt debt
         left join depo_data.debt debtBeforeMonth
                   on debt.id = debtBeforeMonth.id
         left join depo_data.depositor depositor on debt.debt_depositor = depositor.id
where debt.status in ('REGISTERED', 'HALF_PAID')
  and depositor.personal_account_number is not null
  and debt.bill_formed_date between :dateFrom and :dateTo
-- group by debt.depositor_type, balanceAccountNumber, personalAccountNumber
order by debt.depositor_type;

