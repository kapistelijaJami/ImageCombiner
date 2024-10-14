# USAGE:
# .\combine.ps1 3 3 100 100 combinedName.jpg
# or ..\combine.ps1 3 3 100 100 combined33.png 		if you are in the folder with the images and ps1 script is in the parent folder.
# ^ This combines 3x3 grid of 100x100 pieces to single image. Pieces have to be .jpg, but result can be anything.
# or just replace the filename extension in the for loops.
# Filename format for pieces is img_x-y.jpg
# ImageMagick needs to be installed https://imagemagick.org/script/download.php (update PATH if installer didn't do it and restart powershell)
# Works fine with not too many images, but is slow if there's many pieces and they are big. Made the java version which is way faster.

param (
    [int]$nX = 2,  # Default size is 2x2 grid
	[int]$nY = 2,
	[int]$pieceX = 100,  # Default size is 100x100 for piece
	[int]$pieceY = 100,
	[string]$output = "combined.jpg"  # Default name is combined.jpg
)

Write-Host "Combining ${nX}x${nY} grid of ${pieceX}x${pieceY} pieces to single $($nX * $pieceX)x$($nY * $pieceY) image."

# Create a white background image (ex. 200x200)
magick -size "$($nX * $pieceX)x$($nY * $pieceY)" xc:white -colorspace sRGB $output

# Loop through the grid coordinates
foreach ($y in 0..($nY - 1)) {
	Write-Host "Starting row y = ${y}"
    foreach ($x in 0..($nX - 1)) {
        # Compose the file name
        $filename = "img_$x-$y.jpg"

        # Composite the image onto the combined image
        $x_offset = $x * $pieceX
        $y_offset = $y * $pieceY
        magick $output $filename -geometry +$x_offset+$y_offset -colorspace sRGB -composite $output
    }
}

Write-Host "Done!"