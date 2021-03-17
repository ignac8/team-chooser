import csv
import random

import itertools


def load_players():
    players_csv = csv.reader(open('elpro.csv', encoding='utf-8'))
    players = dict()
    next(players_csv)
    for line in players_csv:
        nick = line[1]
        rankings = list(map(lambda x: float(x.replace(',', '.')), filter(lambda x: x, line[12:])))[-1:-11:-1]
        ranking = sum(rankings) / len(rankings)
        players[nick] = ranking
    return players


def choose_best_team_combination(players):
    first_team_combinations = itertools.combinations(players, int(len(players) / 2))
    teams_combinations = map(lambda first_team: (first_team, set(players) - set(first_team)), first_team_combinations)
    best_teams_combination = min(teams_combinations, key=get_strength_difference)
    text = "\n".join((f"Team 1: {get_names(best_teams_combination[0])}",
                      f"Team 2: {get_names(best_teams_combination[1])}",
                      f"Diff: {get_strength_difference(best_teams_combination)}"))
    return text


def get_names(team):
    return ", ".join(map(lambda y: y[0], team))


def get_strength_difference(team_combination):
    return abs(get_player_strength(team_combination[0]) - get_player_strength(team_combination[1]))


def get_player_strength(one_team):
    return sum(map(lambda player: player[1], one_team))


if __name__ == '__main__':
    players = load_players()
    print(players)
    best_teams_combination = choose_best_team_combination(random.sample(list(players.items())[0:20], 10))
    print(best_teams_combination)
