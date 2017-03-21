# -*- coding: utf-8 -*-
import sys
import os
import subprocess
from PIL import Image
from PIL import ImageDraw
from PIL import ImageFont
from EPD import EPD

WHITE = 1
BLACK = 0

FONT_FILE = '/usr/share/fonts/truetype/droid/DroidSansMono.ttf'
FONT_SIZE = 70

FILE_TEMPERATURE_INSIDE = '/home/pi/na-temperature-in.txt'
FILE_TEMPERATURE_OUTSIDE = '/home/pi/na-temperature-out.txt'
FILE_IMAGE_INSIDE = '/home/pi/gratis/PlatformWithOS/demo/inside.png'
FILE_IMAGE_OUTSIDE = '/home/pi/gratis/PlatformWithOS/demo/outside.png' 

def main():

    # initialize EPD
    epd = EPD()
    epd.clear()

    # initially set all white background
    image = Image.new('1', epd.size, WHITE)

    # prepare for drawing
    draw = ImageDraw.Draw(image)
    width, height = image.size

    # load font
    font = ImageFont.truetype(FONT_FILE, FONT_SIZE)

    # initialize image
    draw.rectangle((0, 0, width, height), fill=WHITE, outline=WHITE)

    # read data files
    output_in = subprocess.check_output(["cat", FILE_TEMPERATURE_INSIDE])
    output_out = subprocess.check_output(["cat", FILE_TEMPERATURE_OUTSIDE])

    # insert image inside
    in_image = Image.open(FILE_IMAGE_INSIDE)
    resized_image = in_image.resize((70,70))
    bw_rs_image = resized_image.convert("1", dither=Image.FLOYDSTEINBERG)
    offset = 0, 0
    image.paste(bw_rs_image, offset)

    # insert image outside
    out_image = Image.open(FILE_IMAGE_OUTSIDE)
    resized_image = out_image.resize((70,70))
    bw_rs_image = resized_image.convert("1", dither=Image.FLOYDSTEINBERG)
    offset = 0, (height/2)
    image.paste(bw_rs_image, offset)

    # Draw data to image
    # Add degree by adding "+ chr(176) + "C""
    draw.text((75, 0), str(output_in), fill=BLACK, font=font)
    draw.text((75, height/2), str(output_out), fill=BLACK, font=font)

    # Draw image at screen
    epd.display(image)
    epd.partial_update()

# main
if "__main__" == __name__:
    try:
        main()
    except KeyboardInterrupt:
        sys.exit('interrupted')
        pass
