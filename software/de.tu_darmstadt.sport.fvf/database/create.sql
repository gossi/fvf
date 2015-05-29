CREATE TABLE person (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(45) NULL,
  age INTEGER NULL
);

CREATE  TABLE IF NOT EXISTS test (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
  date DATETIME NULL ,
  note TEXT NULL ,
  leds INTEGER NULL ,
  start_frequency DOUBLE NULL ,
  frequency_step DOUBLE NULL ,
  led_pause DOUBLE NULL ,
  cycle_pause DOUBLE NULL ,
  cycles INTEGER NULL ,
  light_dark DOUBLE NULL ,
  stop_criteria INTEGER NULL ,
  person_id INTEGER NOT NULL ,
  CONSTRAINT fk_test_person
    FOREIGN KEY (person_id )
    REFERENCES person (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE  TABLE IF NOT EXISTS run (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
  run INT NOT NULL ,
  frequency DOUBLE NULL ,
  off_led INTEGER NULL ,
  person_led INTEGER NULL ,
  test_id INTEGER NOT NULL ,
  CONSTRAINT fk_test_run_test1
    FOREIGN KEY (test_id )
    REFERENCES test (id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);