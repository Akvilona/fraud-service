CREATE TABLE IF NOT EXISTS fraud_users
(
    id    BIGSERIAL,
    name  VARCHAR(255),
    email VARCHAR(255),
    CONSTRAINT fraud_users_pkey_id PRIMARY KEY (id)
);

comment on table fraud_users is 'Данные по фродовым пользователям';
comment on column fraud_users.id is 'Уникальный идентификатор записи в текущей таблице';
comment on column fraud_users.name is 'Имя пользователя';
comment on column fraud_users.email is 'Email пользователя';

CREATE TABLE IF NOT EXISTS depo_data.debt
(
    id                 SERIAL,
    bill_id            INT,
    depositor_type     VARCHAR(255),
    end_service_period DATE,
    status             VARCHAR(50),
    bill_formed_date   DATE,
    amount_without_vat DECIMAL(18, 2),
    vat_amount         DECIMAL(18, 2),
    remainder          DECIMAL(18, 2),
    debt_depositor     INT,
    CONSTRAINT debt_pkey_id PRIMARY KEY (id)
);

comment on table depo_data.debt is 'Данные по debt';
comment on column depo_data.debt.id is 'Уникальный идентификатор записи в текущей таблице';

CREATE TABLE IF NOT EXISTS depo_data.depositor
(
    id                      SERIAL,
    personal_account_number VARCHAR(20) UNIQUE,
    CONSTRAINT depositor_pkey_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS depo_data.pay
(
    id      SERIAL,
    bill_id INT,
    amount  DECIMAL(18, 2),
    status  VARCHAR(50),
    CONSTRAINT pay_pkey_id PRIMARY KEY (id)
);
