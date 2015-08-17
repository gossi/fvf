# Flicker-Fusion-Frequency Measurement System

Read:

- [Documentation](http://fvf.readthedocs.org)
- [Manual](http://fvf-manual.readthedocs.org)

## Documentation in TU-Design

Generating the docs in TU-Design is a manual process. Take the latex output from sphinx and manually transfer it in the respective `.tex` files.

On mac, you can use [Texnicle](http://www.bobsoft-mac.de/texnicle/texnicle.html) as an LaTeX-IDE to typeset the pdfs, anyway use this shell-script:

```
#!/bin/sh
#
# This is a pdflatex engine for TeXnicle. 
#
# It runs pdflatex the desired number of times, optionally running bibtex after the first run.
#
# <support>nCompile,doBibtex,outputDirectory</support>
#
#  pdflatex.engine
#  TeXnicle
#
# DO NOT EDIT THIS ENGINE. It will be overwritten each time TeXnicle starts up.
#

if [ $# -lt 2 ]
then
echo "usage: <file-to-compile> <working-directory> (<num compile> <do bibtex> <do ps2pdf> <run number> <bibtexCommand> <outputDirectory>)"
exit
fi

# Executable values
PATH=/usr/texbin:/usr/local/bin:$PATH
ENGINE=/usr/texbin/pdflatex
TEXBIN=/usr/texbin

# Process inputs.
# TeXnicle passes the file to be processed as the first input to this 
# script and the working directory as the second input. Other options follow.
mainfile=$1
projectDir=$2
nCompile=$3
doBibtex=$4
doPS2PDF=$5
runNumber=$6
bibtexCommand=$7
outputDir=$8

echo "****************************"
echo "*** Typsetting: $mainfile"
echo "*** Output dir: $projectDir"
echo "*** Output dir: $outputDir"
echo "***  N typeset: $nCompile"
echo "***  Do bibtex: $doBibtex ($bibtexCommand)"
echo "****************************"

# Go to the working directory
cd "$projectDir"

echo " "
echo "***------------------------------------------------------------"
echo "*** Run $runNumber of $nCompile..."
echo "***------------------------------------------------------------"
$ENGINE -synctex=1 -file-line-error -interaction=nonstopmode --output-directory="$outputDir" "$mainfile"

# if this is after the first run, run bibtex if requested
if [ $runNumber -eq 1 ]
then
  if [ $doBibtex -eq 1 ]
  then
    echo "***------------------------------------------------------------"
    echo "*** Running $bibtexCommand on $mainfile ..."
    echo "***------------------------------------------------------------"
    $TEXBIN/$bibtexCommand "$mainfile"
  fi
fi

echo "*** pdflatex.engine has completed."

# END
```
