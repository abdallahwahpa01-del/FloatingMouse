#!/usr/bin/env bash

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -ld "$PRG"
    link=`ls -l "$PRG" | awk '{print $NF}'`
    case $link in
      /*) PRG="$link" ;;
      *) PRG=`dirname "$PRG"`/"$link" ;;
    esac
done
SAVED="$(cd "$(dirname \"$PRG\")" && pwd)"
cd "$SAVED" || exit
APP_HOME="$(cd "$(dirname \"$SAVED\")" && pwd)"

SET_SHELL_APPEND_VERSION="false"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add a user-defined property if necessary
if [ -z "$GRADLE_OPTS" ] ; then
    GRADLE_OPTS="-Xmx64m -Xms64m"
fi

# Use the maximum available, or set MAX if you prefer
MAX="512m"
if [ -z "$GRADLE_OPTS" ] ; then
    GRADLE_OPTS="-Xmx$MAX"
else
    GRADLE_OPTS="$GRADLE_OPTS -Xmx$MAX"
fi

exec "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"