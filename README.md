# COVID-19 Tracker

**About application** <br />
COVID-19 tracker is the application to track the current situation of COVID-19
over global field and country field.

**How to use** <br />
Before starting the program, users need to assign path to keep the information, path can be both relative path and absolute path. 
To change path, 
1. Go to tracker.properties
2. In tracker.properties, users will see 

download.case = URL1 <br />
download.death = URL2 <br />
download.recovery = URL3 <br />
data.dir = /Home/folder_to_keep_info <br />

What the user needs to modify is /Home/folder_to_keep_info
For example, if the information should be kept at /User/keepinfo,
data.dir changes to <br />

*data.dir = /User/keepinfo*

The 3 URLs above is used to download information from the internet, so
**_DO NOT TOUCH THE URLS_**

To use the application,
1. Go to Main.java
2. Run Main.main
3. The GUI window will pop-up at the homepage which shows the summary of global situation.
4. At the top of the page, there will be search box for each country. Select the coutry to view and click GO button and the information of the chosen coutry will be shown.
5. To go back to homepage click back button.
<br />
note: The information files are provided in tracker/information but maybe not up-to-date.
The users can delete this folder, if they want to store the information in another folder.
However, if tracker/information is used as a path, the user needs to modify the path in tracker.properties as well.
Just assign the right path to information keeping folder in tracker.properties as described above. <br />
<br />

reference information:
1. https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv
2. https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv
3. https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv