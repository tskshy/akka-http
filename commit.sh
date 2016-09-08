#!/usr/bin/env sh

INFO=$1

if [ ! -n "$INFO" ]; then
    echo "you can commit with your update info, e.g.: ./commit.sh 'UPDATE INFO'"
	INFO="default update"
fi

git pull
git add -A
git commit -m "$INFO"
git push
