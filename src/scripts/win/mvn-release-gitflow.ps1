param(
    [string]$release = $(throw "-release is required"),
    [string]$next = $(throw "-next is required")
)

$gitflow = git flow config

if (!$gitflow) {
    exit
}

git flow release start $release

mvn clean test versions::set -DnewVersion="$release"
git commit -a -m "Set release version"

mvn deploy

git flow release finish $release -m "$release"

mvn versions::set -Dnew="$next-SNAPSHOT"
git commit -a -m "Set next development version"
