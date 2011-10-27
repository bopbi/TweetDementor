SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `idtweet` ;
CREATE SCHEMA IF NOT EXISTS `idtweet` DEFAULT CHARACTER SET latin1 ;
USE `idtweet` ;

-- -----------------------------------------------------
-- Table `idtweet`.`followers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `idtweet`.`followers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `user_id` INT(11) NULL DEFAULT NULL ,
  `followers` LONGTEXT NULL DEFAULT NULL ,
  `created_at` DATETIME NULL DEFAULT NULL ,
  `updated_at` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `USER` (`user_id` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `idtweet`.`point`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `idtweet`.`point` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `lat` DOUBLE NULL DEFAULT NULL ,
  `lng` DOUBLE NULL DEFAULT NULL ,
  `radius` INT(11) NULL DEFAULT NULL ,
  `unit` VARCHAR(3) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 34
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `idtweet`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `idtweet`.`user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `user_id` INT(11) NULL DEFAULT NULL ,
  `user_name` VARCHAR(255) NULL DEFAULT NULL ,
  `text` VARCHAR(255) NULL DEFAULT NULL ,
  `lat` DOUBLE NULL DEFAULT NULL ,
  `lng` DOUBLE NULL DEFAULT NULL ,
  `created_at` DATETIME NULL DEFAULT NULL ,
  `updated_at` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 139207
DEFAULT CHARACTER SET = latin1;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
