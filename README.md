#  D7032E - Software Engineering
Home exam of the course D7032E - Software Engineering @ LTU.

# Setup
To compile and run all of the unittest run (while in the KingTokyoPowerUp directory):
```bash
bash compile.sh
bash unittest.sh
```
To then play the game do:
```bash
cd bin
java server.Server <num players>
```
Then open up one terminal for each specified player and do (while in the bin directory):
```bash
java client.Client
```
