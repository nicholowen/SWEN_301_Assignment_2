Special instructions:

Download the VisualVM zip file from 'https://visualvm.github.io/download.html'<br>
Extract zip file<br>
Run the shell script in the bin folder<br>
Add the mbeans plugin from Toos -> Plugins<br>
Run the LogRunner and select the appropriate application from the Application tree on VisualVM.<br>
Can monitor from the MBeans tab on the right.<br>


JSON Parser - Gson<br>

Both Gson and Jackson are arguably the two of the most widely used JSON object serializers and deserializers.<br>
So as I didn't have any experience with JSON, it was logical to choose between the two.<br>

After some research, I decided to user Gson on the following factors:<br>

1. Was originally built inside google and is still maintained by them.<br>
2. Gson is opensource and is actively developed with a large, loyal, comminuty base.<br>
3. Gson has been used in a number of Android apps<br>
4. I refered to the team member in charge of persistence in a previous team project. They used Gson and had recommended it.

