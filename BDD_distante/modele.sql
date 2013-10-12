SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`GpsGeom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`GpsGeom` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`GpsGeom` (
  `gpsGeom_id` INT(11) NOT NULL ,
  `gpsGeom_the_geom` GEOMETRY NULL ,
  PRIMARY KEY (`gpsGeom_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Project` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Project` (
  `project_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `project_name` VARCHAR(60) NOT NULL ,
  `gpsGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`project_id`) ,
  UNIQUE INDEX `projet_id_UNIQUE` (`project_id` ASC) ,
  INDEX `fk_Project_GpsGeom1_idx` (`gpsGeom_id` ASC) ,
  CONSTRAINT `fk_Project_GpsGeom1`
    FOREIGN KEY (`gpsGeom_id` )
    REFERENCES `mydb`.`GpsGeom` (`gpsGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Photo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Photo` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Photo` (
  `photo_id` INT(11) NOT NULL ,
  `photo_description` TEXT NULL ,
  `photo_author` VARCHAR(60) NULL ,
  `project_id` INT(11) NOT NULL ,
  `gpsGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`photo_id`) ,
  UNIQUE INDEX `photo_id_UNIQUE` (`photo_id` ASC) ,
  INDEX `fk_Photo_Project_idx` (`project_id` ASC) ,
  INDEX `fk_Photo_GpsGeom1_idx` (`gpsGeom_id` ASC) ,
  CONSTRAINT `fk_Photo_Project`
    FOREIGN KEY (`project_id` )
    REFERENCES `mydb`.`Project` (`project_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Photo_GpsGeom1`
    FOREIGN KEY (`gpsGeom_id` )
    REFERENCES `mydb`.`GpsGeom` (`gpsGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Material` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Material` (
  `material_id` INT(11) NOT NULL ,
  `material_name` VARCHAR(90) NOT NULL ,
  PRIMARY KEY (`material_id`) ,
  UNIQUE INDEX `material_id_UNIQUE` (`material_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PixelGeom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`PixelGeom` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`PixelGeom` (
  `pixelGeom_id` INT(11) NOT NULL ,
  `pixelGeom_the_geom` GEOMETRY NULL ,
  PRIMARY KEY (`pixelGeom_id`) ,
  UNIQUE INDEX `pixelGeom_id_UNIQUE` (`pixelGeom_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Roof`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Roof` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Roof` (
  `roof_id` INT(11) NOT NULL ,
  `pixelGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`roof_id`) ,
  UNIQUE INDEX `roof_id_UNIQUE` (`roof_id` ASC) ,
  INDEX `fk_Roof_PixelGeom1_idx` (`pixelGeom_id` ASC) ,
  CONSTRAINT `fk_Roof_PixelGeom1`
    FOREIGN KEY (`pixelGeom_id` )
    REFERENCES `mydb`.`PixelGeom` (`pixelGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Ground`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Ground` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Ground` (
  `ground_id` INT(11) NOT NULL ,
  `pixelGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`ground_id`) ,
  UNIQUE INDEX `floor_id_UNIQUE` (`ground_id` ASC) ,
  INDEX `fk_Ground_PixelGeom1_idx` (`pixelGeom_id` ASC) ,
  CONSTRAINT `fk_Ground_PixelGeom1`
    FOREIGN KEY (`pixelGeom_id` )
    REFERENCES `mydb`.`PixelGeom` (`pixelGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Frontage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Frontage` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Frontage` (
  `frontage_id` INT(11) NOT NULL ,
  `photo_id` INT(11) NOT NULL ,
  `material_id` INT(11) NOT NULL ,
  `roof_id` INT(11) NOT NULL ,
  `ground_id` INT(11) NOT NULL ,
  `gpsGeom_id` INT(11) NOT NULL ,
  `pixelGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`frontage_id`) ,
  UNIQUE INDEX `facade_id_UNIQUE` (`frontage_id` ASC) ,
  INDEX `fk_Frontage_Photo1_idx` (`photo_id` ASC) ,
  INDEX `fk_Frontage_Material1_idx` (`material_id` ASC) ,
  INDEX `fk_Frontage_Roof1_idx` (`roof_id` ASC) ,
  INDEX `fk_Frontage_Ground1_idx` (`ground_id` ASC) ,
  INDEX `fk_Frontage_GpsGeom1_idx` (`gpsGeom_id` ASC) ,
  INDEX `fk_Frontage_PixelGeom1_idx` (`pixelGeom_id` ASC) ,
  CONSTRAINT `fk_Frontage_Photo1`
    FOREIGN KEY (`photo_id` )
    REFERENCES `mydb`.`Photo` (`photo_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Frontage_Material1`
    FOREIGN KEY (`material_id` )
    REFERENCES `mydb`.`Material` (`material_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Frontage_Roof1`
    FOREIGN KEY (`roof_id` )
    REFERENCES `mydb`.`Roof` (`roof_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Frontage_Ground1`
    FOREIGN KEY (`ground_id` )
    REFERENCES `mydb`.`Ground` (`ground_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Frontage_GpsGeom1`
    FOREIGN KEY (`gpsGeom_id` )
    REFERENCES `mydb`.`GpsGeom` (`gpsGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Frontage_PixelGeom1`
    FOREIGN KEY (`pixelGeom_id` )
    REFERENCES `mydb`.`PixelGeom` (`pixelGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Type` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Type` (
  `type_id` INT(11) NOT NULL ,
  `type_name` VARCHAR(90) NULL ,
  PRIMARY KEY (`type_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FrontageElement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`FrontageElement` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`FrontageElement` (
  `frontageElement_id` INT(11) NOT NULL ,
  `frontage_id` INT(11) NOT NULL ,
  `material_id` INT(11) NOT NULL ,
  `type_id` INT(11) NOT NULL ,
  `pixelGeom_id` INT(11) NOT NULL ,
  PRIMARY KEY (`frontageElement_id`) ,
  UNIQUE INDEX `facadeElement_id_UNIQUE` (`frontageElement_id` ASC) ,
  INDEX `fk_FrontageElement_Frontage1_idx` (`frontage_id` ASC) ,
  INDEX `fk_FrontageElement_Material1_idx` (`material_id` ASC) ,
  INDEX `fk_FrontageElement_Type1_idx` (`type_id` ASC) ,
  INDEX `fk_FrontageElement_PixelGeom1_idx` (`pixelGeom_id` ASC) ,
  CONSTRAINT `fk_FrontageElement_Frontage1`
    FOREIGN KEY (`frontage_id` )
    REFERENCES `mydb`.`Frontage` (`frontage_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FrontageElement_Material1`
    FOREIGN KEY (`material_id` )
    REFERENCES `mydb`.`Material` (`material_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FrontageElement_Type1`
    FOREIGN KEY (`type_id` )
    REFERENCES `mydb`.`Type` (`type_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FrontageElement_PixelGeom1`
    FOREIGN KEY (`pixelGeom_id` )
    REFERENCES `mydb`.`PixelGeom` (`pixelGeom_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `mydb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
