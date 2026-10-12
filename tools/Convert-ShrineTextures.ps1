param()
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
$projectRoot = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot '..'))
$sourceDirectory = Join-Path $projectRoot 'docs/art-source'
$destinationDirectory = Join-Path $projectRoot 'src/main/resources/assets/deep_end/textures'
foreach ($textureName in @('ancient_shrine_stone', 'ancient_crystal', 'ancient_rune_inlay', 'ancient_resonant_crystal')) {
    $sourcePath = Join-Path $sourceDirectory "$textureName.png"
    $textureFolder = if ($textureName -eq 'ancient_resonant_crystal') { 'item' } else { 'block' }
    $destinationPath = Join-Path $destinationDirectory "$textureFolder/$textureName.png"
    $sourceImage = [Drawing.Bitmap]::new($sourcePath)
    $pixelTexture = [Drawing.Bitmap]::new(32, 32)
    try {
        for ($y = 0; $y -lt 32; $y++) {
            for ($x = 0; $x -lt 32; $x++) {
                $sourceX = [Math]::Min($sourceImage.Width - 1, [int][Math]::Floor(($x + 0.5) * $sourceImage.Width / 32))
                $sourceY = [Math]::Min($sourceImage.Height - 1, [int][Math]::Floor(($y + 0.5) * $sourceImage.Height / 32))
                $pixelTexture.SetPixel($x, $y, $sourceImage.GetPixel($sourceX, $sourceY))
            }
        }
        $pixelTexture.Save($destinationPath, [Drawing.Imaging.ImageFormat]::Png)
        Write-Output "$textureName -> 32x32"
    } finally {
        $pixelTexture.Dispose()
        $sourceImage.Dispose()
    }
}
