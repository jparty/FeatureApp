-- Enable PostGIS (includes raster)
CREATE EXTENSION IF NOT EXISTS postgis;
-- Enable Topology
CREATE EXTENSION IF NOT EXISTS postgis_topology;
-- fuzzy matching needed for Tiger
CREATE EXTENSION IF NOT EXISTS fuzzystrmatch;
-- Enable US Tiger Geocoder
CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder;

CREATE SCHEMA IF NOT EXISTS mydb ;

-- -----------------------------------------------------
-- Table mydb.GpsGeom
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.GpsGeom CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.GpsGeom (
  gpsGeom_id SERIAL ,
  gpsGeom_the_geom GEOMETRY NULL ,
  PRIMARY KEY (gpsGeom_id) );


-- -----------------------------------------------------
-- Table mydb.Project
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Project CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Project (
  project_id SERIAL ,
  project_name VARCHAR(60) NOT NULL ,
  gpsGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (project_id) ,
  CONSTRAINT fk_Project_GpsGeom1
    FOREIGN KEY (gpsGeom_id )
    REFERENCES mydb.GpsGeom (gpsGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX project_id_UNIQUE ON mydb.Project (project_id);
CREATE INDEX fk_Project_GpsGeom1_idx ON mydb.Project (gpsGeom_id);


-- -----------------------------------------------------
-- Table mydb.Photo
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Photo CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Photo (
  photo_id SERIAL ,
  photo_description TEXT NULL ,
  photo_author VARCHAR(60) NULL ,
  project_id INTEGER NOT NULL ,
  gpsGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (photo_id) ,
  CONSTRAINT fk_Photo_Project
    FOREIGN KEY (project_id )
    REFERENCES mydb.Project (project_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Photo_GpsGeom1
    FOREIGN KEY (gpsGeom_id )
    REFERENCES mydb.GpsGeom (gpsGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX photo_id_UNIQUE ON mydb.Photo (photo_id);
CREATE INDEX fk_Photo_Project_idx ON mydb.Photo (project_id);
CREATE INDEX fk_Photo_GpsGeom1_idx ON mydb.Photo (gpsGeom_id);


-- -----------------------------------------------------
-- Table mydb.Material
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Material CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Material (
  material_id SERIAL ,
  material_name VARCHAR(90) NOT NULL ,
  PRIMARY KEY (material_id) );
  CREATE UNIQUE INDEX material_id_UNIQUE ON mydb.Material (material_id);


-- -----------------------------------------------------
-- Table mydb.PixelGeom
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.PixelGeom CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.PixelGeom (
  pixelGeom_id SERIAL ,
  pixelGeom_the_geom GEOMETRY NULL ,
  PRIMARY KEY (pixelGeom_id) );
  CREATE UNIQUE INDEX pixelGeom_id_UNIQUE ON mydb.PixelGeom (pixelGeom_id);


-- -----------------------------------------------------
-- Table mydb.Roof
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Roof CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Roof (
  roof_id SERIAL ,
  pixelGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (roof_id) ,
  CONSTRAINT fk_Roof_PixelGeom1
    FOREIGN KEY (pixelGeom_id )
    REFERENCES mydb.PixelGeom (pixelGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX roof_id_UNIQUE ON mydb.Roof (roof_id);
CREATE INDEX fk_Roof_PixelGeom1_idx ON mydb.Roof (pixelGeom_id);


-- -----------------------------------------------------
-- Table mydb.Ground
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Ground CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Ground (
  ground_id SERIAL ,
  pixelGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (ground_id) ,
  CONSTRAINT fk_Ground_PixelGeom1
    FOREIGN KEY (pixelGeom_id )
    REFERENCES mydb.PixelGeom (pixelGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX floor_id_UNIQUE ON mydb.Ground (ground_id);
CREATE INDEX fk_Ground_PixelGeom1_idx ON mydb.Ground (pixelGeom_id);


-- -----------------------------------------------------
-- Table mydb.Frontage
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Frontage CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Frontage (
  frontage_id SERIAL ,
  photo_id INTEGER NOT NULL ,
  material_id INTEGER NOT NULL ,
  roof_id INTEGER NOT NULL ,
  ground_id INTEGER NOT NULL ,
  gpsGeom_id INTEGER NOT NULL ,
  pixelGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (frontage_id) ,
  CONSTRAINT fk_Frontage_Photo1
    FOREIGN KEY (photo_id )
    REFERENCES mydb.Photo (photo_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Frontage_Material1
    FOREIGN KEY (material_id )
    REFERENCES mydb.Material (material_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Frontage_Roof1
    FOREIGN KEY (roof_id )
    REFERENCES mydb.Roof (roof_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Frontage_Ground1
    FOREIGN KEY (ground_id )
    REFERENCES mydb.Ground (ground_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Frontage_GpsGeom1
    FOREIGN KEY (gpsGeom_id )
    REFERENCES mydb.GpsGeom (gpsGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_Frontage_PixelGeom1
    FOREIGN KEY (pixelGeom_id )
    REFERENCES mydb.PixelGeom (pixelGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX facade_id_UNIQUE ON mydb.Frontage (frontage_id);
CREATE INDEX fk_Frontage_Photo1_idx ON mydb.Frontage (photo_id);
CREATE INDEX fk_Frontage_Material1_idx ON mydb.Frontage (material_id);
CREATE INDEX fk_Frontage_Roof1_idx ON mydb.Frontage (roof_id);
CREATE INDEX fk_Frontage_Ground1_idx ON mydb.Frontage (ground_id);
CREATE INDEX fk_Frontage_GpsGeom1_idx ON mydb.Frontage (gpsGeom_id);
CREATE INDEX fk_Frontage_PixelGeom1_idx ON mydb.Frontage (pixelGeom_id);


-- -----------------------------------------------------
-- Table mydb.Type
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.Type CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.Type (
  type_id SERIAL ,
  type_name VARCHAR(90) NULL ,
  PRIMARY KEY (type_id) );


-- -----------------------------------------------------
-- Table mydb.FrontageElement
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.FrontageElement CASCADE ;

CREATE  TABLE IF NOT EXISTS mydb.FrontageElement (
  frontageElement_id SERIAL ,
  frontage_id INTEGER NOT NULL ,
  material_id INTEGER NOT NULL ,
  type_id INTEGER NOT NULL ,
  pixelGeom_id INTEGER NOT NULL ,
  PRIMARY KEY (frontageElement_id) ,
  CONSTRAINT fk_FrontageElement_Frontage1
    FOREIGN KEY (frontage_id )
    REFERENCES mydb.Frontage (frontage_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FrontageElement_Material1
    FOREIGN KEY (material_id )
    REFERENCES mydb.Material (material_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FrontageElement_Type1
    FOREIGN KEY (type_id )
    REFERENCES mydb.Type (type_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_FrontageElement_PixelGeom1
    FOREIGN KEY (pixelGeom_id )
    REFERENCES mydb.PixelGeom (pixelGeom_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
CREATE UNIQUE INDEX facadeElement_id_UNIQUE ON mydb.FrontageElement (frontageElement_id);
CREATE INDEX fk_FrontageElement_Frontage1_idx ON mydb.FrontageElement (frontage_id);
CREATE INDEX fk_FrontageElement_Material1_idx ON mydb.FrontageElement (material_id);
CREATE INDEX fk_FrontageElement_Type1_idx ON mydb.FrontageElement (type_id);
CREATE INDEX fk_FrontageElement_PixelGeom1_idx ON mydb.FrontageElement (pixelGeom_id);