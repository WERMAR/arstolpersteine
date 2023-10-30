CREATE TABLE Victim
(
  Name VARCHAR(30) NOT NULL,
  Last_Name VARCHAR(30) NOT NULL,
  Date_of_birth DATE NOT NULL,
  Date_of_death DATE NOT NULL,
  VictimID INT NOT NULL,
  PRIMARY KEY (VictimID)
);

CREATE TABLE Role
(
  RoleID INT NOT NULL,
  Role_name VARCHAR(15) NOT NULL,
  PRIMARY KEY (RoleID)
);

CREATE TABLE Users
(
  UserID INT NOT NULL,
  password VARCHAR(30) NOT NULL,
  login_email VARCHAR(50) NOT NULL,
  Name VARCHAR(30) NOT NULL,
  Last_Name VARCHAR(30) NOT NULL,
  RoleID INT NOT NULL,
  PRIMARY KEY (UserID),
  FOREIGN KEY (RoleID) REFERENCES Role(RoleID)
);

CREATE TABLE Stolperstein
(
  StolpersteinID INT NOT NULL,
  Description VARCHAR NOT NULL,
  Approved Boolean NOT NULL,
  UserID INT NOT NULL,
  VictimID INT NOT NULL,
  PRIMARY KEY (StolpersteinID),
  FOREIGN KEY (UserID) REFERENCES Users(UserID),
  FOREIGN KEY (VictimID) REFERENCES Victim(VictimID)
);

CREATE TABLE Address
(
  AddressID INT NOT NULL,
  Streetname VARCHAR(50) NOT NULL,
  HouseNumber INT NOT NULL,
  PostCode INT NOT NULL,
  Suburb VARCHAR(50) NOT NULL,
  lon FLOAT NOT NULL,
  lat FLOAT NOT NULL,
  StolpersteinID INT NOT NULL,
  PRIMARY KEY (AddressID),
  FOREIGN KEY (StolpersteinID) REFERENCES Stolperstein(StolpersteinID)
);

CREATE TABLE Photo
(
  PhotoID INT NOT NULL,
  lat FLOAT NOT NULL,
  Heading VARCHAR(10) NOT NULL,
  lon FLOAT NOT NULL,
  Approved boolean NOT NULL,
  UserID INT NOT NULL,
  StolpersteinID INT NOT NULL,
  approveUserID INT NOT NULL,
  PRIMARY KEY (PhotoID),
  FOREIGN KEY (UserID) REFERENCES Users(UserID),
  FOREIGN KEY (StolpersteinID) REFERENCES Stolperstein(StolpersteinID),
  FOREIGN KEY (approveUserID) REFERENCES Users(UserID)
);

