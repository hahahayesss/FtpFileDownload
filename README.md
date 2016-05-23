<p><h1>Ftp Log Files Downloader</h1>
If you want to use this, You need to give "3h-ftp.properties" file. Create this file in same folder.<br>
<br>
Open comman panel and write this command.<br>
<i>java -jar FtpFileDownload.jar 3h-ftp.properties</i><br>
<br>
For ".sh" file...<br>
<i>nohup java –jar ./rsm-audit-parser.jar ./rsm-audit-parser.properties &</i><br></p>

<p><b>3h-ftp.properties</b><br>
You need to write some information in this file.</p>

<p><b>ftp.server.address</b><br>
You have to write your ftp server IP address.<br>
Example : <i>ftp.server.address=192.168.75.130</i></p>

<p><b>ftp.server.port</b><br>
If your ftp server is using to default port which is 21, you don't have to give port number.<br>
Example : <i>ftp.server.port=521</i></p>

<p><b>ftp.server.username</b><br>
You have to write your ftp server user name.<br>
Example : <i>ftp.server.username=yourUserName</i></p>

<p><b>ftp.server.userpassword</b><br>
You have to write your ftp server user password.<br>
Example : <i>ftp.server.userpassword=yourUserPassword</i></p>

<p><b>ftp.file.path</b><br>
You can write ftp files path.<br>
Example : <i>ftp.file.path=FtpDownloaderTest</i></p>

<p><b>ftp.file.name</b><br>
You have to write file name. For example, all your log files format is ".log". You need to write "*.log".<br>
Another example, If all your log files name's start with "00", You need to write "00*"<br>
Example : <i>ftp.file.name=*.log</i></p>

<p><b>ftp.file.download.limit</b><br>
You have to write. For example, Your ftp server has 100 log file and this program start to work every 10 min.<br>
This program just download 10 file every 10 min.<br>
(If you don't write anything, every time it will download 25 files)<br>
Example : <i>ftp.file.download.limit=5</i></p>

<p><b>downloaded.file.location</b><br>
You have to write download location.<br>
(Local computer)<br>
Example : <i>D:/DownloadedFiles</i></p>

<p><b>log.path</b><br>
You have to write log file location.<br>
(Local computer)<br>
Example : <i>D:/FtpDownloaderLogFile/download.log</i></p>

<p><b>FTPDownloadProcessTrigger.expression</b><br>
Default=0 0/5 * * * ?     //Every 5 min.<br>
If you don't know what this is, You need to look this link.<br>
http://www.quartz-scheduler.org/documentation/quartz-2.1.x/tutorials/crontrigger.html</p>

<p>Sorry for my english.</p>
