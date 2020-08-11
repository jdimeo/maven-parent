mvn release:prepare release:perform
if ($?) {
$curr = ((git rev-parse HEAD) | Out-String).trim()
$prev = ((git rev-parse HEAD^1) | Out-String).trim()
git checkout master
git merge --no-ff $prev -m "Merging release into master"
git checkout develop
git merge --no-ff $curr -m "Preparing develop for next iteration"
}