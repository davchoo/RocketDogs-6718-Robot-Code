import sys
import pygame
import csv
import math

#Create screen
size = width, height = 800, 600
pygame.init()
screen = pygame.display.set_mode(size)

#Clock
clock = pygame.time.Clock()

#Colors
black = 0, 0, 0
red = 255, 0, 0
yellow = 255, 255, 0

#Read csv

f = open("path.csv")
reader = csv.reader(f)

current_segment = 1

positions_actual = []
positions_only_vel = []

x = 0
y = 0

firstLine = True

for segment in reader:
    if firstLine:
        firstLine = False
        continue
    segment_float = []
    for item in segment:
        segment_float.append(float(item))
    segment = segment_float

    positions_actual.append((segment[1] + 400, 600 - (segment[2] + 300)))
    x += math.sin(2 * math.pi - segment[7] + 0.5 * math.pi) * segment[4] * 0.02
    y += math.cos(2 * math.pi - segment[7] + 0.5 * math.pi) * segment[4] * 0.02
    positions_only_vel.append((x + 400, 600 - (y + 300)))

#Display loop
while 1:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
    #Render
    screen.fill(black)
    pygame.draw.lines(screen, red, False, positions_actual, 1)
    pygame.draw.lines(screen, yellow, False, positions_only_vel, 1)
    pygame.display.flip()
    #Set framerate to 50hz (0.02 ms)
    clock.tick(50)


