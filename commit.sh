#!/usr/bin/env sh

INFO=$1

if [ ! -n "$INFO" ]; then
	INFO="default update"
fi

git pull
git add -A
git commit -m "$INFO"
git push
