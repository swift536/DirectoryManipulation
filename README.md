# DirectoryManipulation

The overall purpose of this project is to assist in the automation of converting my local filesystem into a useable text format that would ultimately be inserted into
Javascript to rebuild the hierchy within my website. I currently have it configured to replace newline characters with spaces to signify where a sub-directory begins.
Future plans include a simple GUI which would allow the navigation to, and complete processing of the directory from within the application.

Simple Java program to manipulate system directory data. Currently using with the windows command 'dir /s > somefolder.txt' to process the raw directory/subdirectory.
Can convert raw text file into a a text based hierarchy representation of the original directory (sub-folders will be indented from their parents). For simple purposes
the processing could be done here. However, it can be taken a step further and replace newline characters to present as a single line of text.
