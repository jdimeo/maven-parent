Write-Host "Start a GitFlow release in your Git client (SourceTree), then press a key to continue..."
$x = $host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

mvn clean test versions::set
git commit -a -m "Set release version"

Write-Host "Finish AND TAG the release in your Git client (SourceTree), then press a key to continue..."
$x = $host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

mvn versions::set
git commit -a -m "Set next development version"