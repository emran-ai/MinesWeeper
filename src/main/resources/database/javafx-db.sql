drop table int_board;

drop table int_tiles;

drop table int_minesweeper;

drop table int_leaderboard;


create table if not exists int_leaderboard (
    player_name varchar(50) not null constraint pk_int_leaderboard primary key
    
);

create table if not exists int_minesweeper(
	game_id numeric (4) not null constraint pk_int_minesweeper_id  primary key
	,player_name varchar(50)
	,start_time timestamp
    ,end_time timestamp
	,game_result varchar(20) --won, lost, in_progress
    ,foreign key (player_name) references int_leaderboard(player_name)
);

create table if not exists int_board(
    field_x numeric (4)
    ,field_y numeric (4)
	,field_value numeric (4)
    ,game_id numeric (4)
	,primary key (game_id, field_x, field_y)
	,foreign key (game_id) references int_minesweeper(game_id)
);

create table if not exists int_tiles(
    field_x numeric (4)
    ,field_y numeric (4)
	,field_value numeric (4)
    ,game_id numeric (4) 
	,primary key (game_id, field_x, field_y)
	,foreign key (game_id) references int_minesweeper(game_id)
);



select * from int_leaderboard;
select * from int_minesweeper;
select * from int_board;
select * from int_tiles;



