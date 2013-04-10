; -- Example1.iss --
; Demonstrates copying 3 files and creating an icon.

; SEE THE DOCUMENTATION FOR DETAILS ON CREATING .ISS SCRIPT FILES!

[Setup]
AppName=nodepad.exe
AppVerName=nodepad.exe 12
DefaultDirName={pf}\nodepad
DefaultGroupName=nodepad
Compression=lzma
SolidCompression=yes
OutputDir=userdocs:Inno Setup Examples Output

[Files]
Source: nodepad.exe; DestDir: {app}
Source: .\*; DestDir: {app}
Source: jre/*; DestDir: {app}\jre; Flags: recursesubdirs
Source: lib/*; DestDir: {app}\lib; Flags: recursesubdirs
Source: data/*; DestDir: {app}\data; Flags: recursesubdirs



[Registry]
Root: HKCR; SubKey: .nd; ValueType: string; ValueData: nodepad; Flags: uninsdeletekey
Root: HKCR; SubKey: nodepad; ValueType: string; ValueData: nodepad; Flags: uninsdeletekey
Root: HKCR; SubKey: nodepad\Shell\Open\Command; ValueType: string; ValueData: """{app}\nodepad.exe"" ""%1"""; Flags: uninsdeletevalue
Root: HKCR; Subkey: nodepad\DefaultIcon; ValueType: string; ValueData: {app}\nodepad.exe,0; Flags: uninsdeletevalue
