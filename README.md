Special instructions:

Download the VisualVM zip file from 'https://visualvm.github.io/download.html'
Extract zip file
Run the shell script in the bin folder
Add the mbeans plugin from Toos -> Plugins
Run the LogRunner and select the appropriate application from the Application tree on VisualVM.
Can monitor from the MBeans tab on the right.


JSON Parser - Gson

Both Gson and Jackson are arguably the two of the most widely used JSON object serializers and deserializers.
So as I didn't have any experience with JSON, it was logical to choose between the two.

After some research, I decided to user Gson on the following factors:

1. Was originally built inside google and is still maintained by them.
2. Gson is opensource and is actively developed with a large, loyal, comminuty base.
3. Gson has been used in a number of Android apps
4. I refered to the team member in charge of persistence in a previous team project. They used Gson and had recommended it.

