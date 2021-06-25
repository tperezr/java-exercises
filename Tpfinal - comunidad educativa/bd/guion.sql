-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tpforo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tpforo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tpforo` DEFAULT CHARACTER SET utf8 ;
USE `tpforo` ;

-- -----------------------------------------------------
-- Table `tpforo`.`tematicas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tpforo`.`tematicas` ;

CREATE TABLE IF NOT EXISTS `tpforo`.`tematicas` (
  `idTematica` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTematica`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tpforo`.`referentes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tpforo`.`referentes` ;

CREATE TABLE IF NOT EXISTS `tpforo`.`referentes` (
  `idReferente` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `rol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idReferente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tpforo`.`problematica`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tpforo`.`problematica` ;

CREATE TABLE IF NOT EXISTS `tpforo`.`problematica` (
  `idProblematica` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `fechaPublicacion` DATE NOT NULL,
  `detalle` VARCHAR(100) NOT NULL,
  `caduco` TINYINT(1) NOT NULL,
  `fuePlanteada` TINYINT(1) NOT NULL,
  `cerrada` TINYINT(1) NOT NULL,
  `observacion` VARCHAR(100) NULL DEFAULT NULL,
  `tematicas_idTematica` INT(11) NOT NULL,
  `referentes_idReferente` INT(11) NOT NULL,
  PRIMARY KEY (`idProblematica`, `tematicas_idTematica`, `referentes_idReferente`),
  INDEX `fk_problematica_tematicas1_idx` (`tematicas_idTematica` ASC),
  INDEX `fk_problematica_referentes1_idx` (`referentes_idReferente` ASC),
  CONSTRAINT `fk_problematica_tematicas1`
    FOREIGN KEY (`tematicas_idTematica`)
    REFERENCES `tpforo`.`tematicas` (`idTematica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_problematica_referentes1`
    FOREIGN KEY (`referentes_idReferente`)
    REFERENCES `tpforo`.`referentes` (`idReferente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tpforo`.`propuestas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tpforo`.`propuestas` ;

CREATE TABLE IF NOT EXISTS `tpforo`.`propuestas` (
  `idPropuesta` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `fechaPublicacion` DATE NOT NULL,
  `detalle` VARCHAR(100) NOT NULL,
  `fechaCaducidad` DATE NOT NULL,
  `tematicas_idTematica` INT(11) NOT NULL,
  PRIMARY KEY (`idPropuesta`, `tematicas_idTematica`),
  INDEX `fk_propuestas_tematicas_idx` (`tematicas_idTematica` ASC),
  CONSTRAINT `fk_propuestas_tematicas`
    FOREIGN KEY (`tematicas_idTematica`)
    REFERENCES `tpforo`.`tematicas` (`idTematica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tpforo`.`tematicas_has_referentes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tpforo`.`tematicas_has_referentes` ;

CREATE TABLE IF NOT EXISTS `tpforo`.`tematicas_has_referentes` (
  `tematicas_idTematica` INT(11) NOT NULL,
  `referentes_idReferente` INT(11) NOT NULL,
  PRIMARY KEY (`tematicas_idTematica`, `referentes_idReferente`),
  INDEX `fk_tematicas_has_referentes_referentes1_idx` (`referentes_idReferente` ASC),
  INDEX `fk_tematicas_has_referentes_tematicas1_idx` (`tematicas_idTematica` ASC),
  CONSTRAINT `fk_tematicas_has_referentes_referentes1`
    FOREIGN KEY (`referentes_idReferente`)
    REFERENCES `tpforo`.`referentes` (`idReferente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tematicas_has_referentes_tematicas1`
    FOREIGN KEY (`tematicas_idTematica`)
    REFERENCES `tpforo`.`tematicas` (`idTematica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
