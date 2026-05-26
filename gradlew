#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

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