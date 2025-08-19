CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS schedulers (
    code UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    date DATE NOT NULL,
    worker UUID NOT NULL,
    UNIQUE (worker, date)
);
CREATE INDEX IF NOT EXISTS idx_worker_date ON schedulers (worker, date);
CREATE TABLE IF NOT EXISTS scheduler_tasks (
    scheduler_code UUID NOT NULL,
    task_code UUID NOT NULL,
    PRIMARY KEY (scheduler_code, task_code),
    FOREIGN KEY (scheduler_code) REFERENCES schedulers(code) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_scheduler_code ON scheduler_tasks (scheduler_code);
