# Ohjelmistotuotantoprojekti 1

Ryhmä R24

## Tuotteen visio:

Tavoitteena on tehdä helppokäyttöinen sovellus, jossa käyttäjät voivat vuokrata, lahjoittaa,
ostaa, myydä ja vaihtaa pelejään. Kaikki pelit käyvät niin lauta- kuin videopelitkin. Käyttäjät
voivat vaihtaa yhteystietonsa ja määrittää tapaamispaikan tai sopia postituksesta. Pelin
vuokraamisen tai myymisen hinnan käyttäjät voivat itse määritellä.

## Kehitysympäristö

Kehitystyökaluna käytettiin Eclipse IDE:tä, jossa on käytetty Maven koontia ja sen kautta JavaFX ja JUnit riippuvuudet. Lokalisointia varten Eclipseen ladattiin lisäosa ResourceBundle Editor. Projektin käyttöliittymä toteutettiin JavaFx:ällä. Käyttöliittymän toteutuksessa hyödynnettiin SceneBuilderia. Projektion tietokanta pyörii Metropolian palvelimella virtuaaliympäristössä EduCloud, jonne asennettiin MySQL tietokanta. Yhteys palvelimeen tapahtuu TCP/IP protokolla ja relaatiotietokantajärjestelmä MariaDB:n kautta. Repositorina käytettiin koko kehitysprosessin ajan tätä GitLab - DevOps verkkopalvelua. Jenkins käännösautomaatio-ohjelmistoa käytettiin jatkuvaan testaukseen projektissa. Jenkins:iin myös lisättiin plug-ins: Build Monitor View, Status Monitor ja JaCoCo Plugin. Projektinhallinta työkaluna käytettiin Nektionia. 

## Sovelluksen käyttö

Sovelluksen käyttämiseksi sinun tulee olla Metropolian sisäverkossa tai käyttää VPN-yhteyttä.
