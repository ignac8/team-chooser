from browser import document, alert
from browser.html import TABLE, TR, TH, SELECT, OPTION, BUTTON

from selecta import load_players, choose_best_team_combination

number_of_players = 10
players = load_players()

table = TABLE()

row = TR()
row <= TH("Gracz")
table <= row

selects = list()
for number in range(number_of_players):
    row = TR()
    select = SELECT()
    for player in players.items():
        select <= OPTION(player[0])
    select.selectedIndex = number
    selects.append(select)
    row <= select
    table <= row
document <= table


def read_choosed_players(ev):
    choosed_players_names = list()
    for select in selects:
        choosed_players_names.append(list(players.items())[select.selectedIndex])
    alert(choose_best_team_combination(choosed_players_names))


button = BUTTON("Wybierz")
button.bind("click", read_choosed_players)
document <= button
