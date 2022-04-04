# Data format translation program

## Create a project

Provided project files are in form of a maven project so you need to create 
new maven project "from existing source" using provided files.

## How to build

Navigate to project root folder DataFormatTranslationProgram and run 
`mvn clean install`. There will be target folder created with two .jar files. 

## How to run
Copy Workbook2.csv and Workbook2.prn files from resources folder to target
folder.
Navigate to target folder created in previous step and run one of the 
following commands to create file from source files:
```
cat ./Workbook2.csv | java -jar DataFormatTranslationProgram-1.0-SNAPSHOT-jar-with-dependencies.jar csv html > csv.html.txt
cat ./Workbook2.csv | java -jar DataFormatTranslationProgram-1.0-SNAPSHOT-jar-with-dependencies.jar csv json > csv.json.txt
cat ./Workbook2.prn | java -jar DataFormatTranslationProgram-1.0-SNAPSHOT-jar-with-dependencies.jar prn html > prn.html.txt
cat ./Workbook2.prn | java -jar DataFormatTranslationProgram-1.0-SNAPSHOT-jar-with-dependencies.jar prn json > prn.json.txt
```

