AutoGenerateApk
===============

AutoGenerateApk is a linux script for generating different apks by using same project

## How to use

1) Rename the source's words which need be replaced.
  
2) Delete all the projects that generated from source project.  
  
3) Copy the "ant_properties" file into the source project's root directory.  

4) Change the variables in the file "linux_script".

5) Run the "linux_script.sh" in terminal.

6) When generating apks operation is over, u can run the "copy_to_one_floder.sh" to get all the released apks if u want.