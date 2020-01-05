%%%-------------------------------------------------------------------
%%% @author eddie
%%% @copyright (C) 2020, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 04. Jan 2020 17:43
%%%-------------------------------------------------------------------
-module(test).
-author("eddie").


%% API
-export([pmap/2, max/1]).



pmap(F, L) ->
  Parent=self(),
  [ receive {Pid, Result} ->  Result end  || Pid <- [spawn(fun() ->  Parent ! {self(), F(X)} end  ) || X <- L ]].

%% erl +P 3000000
max(N) ->
  Max = erlang:system_info(process_limit),
  io:format("Maximum allowed processes: ~p~n",[Max]),
  statistics(runtime),
  statistics(wall_clock),
   L = [spawn(fun()-> receive die-> void end end) || _ <- lists:seq(1,N) ],
  {_,Time1} = statistics(runtime),
  {_,Time2} =  statistics(wall_clock),
  lists:foreach(fun(Pid) -> Pid ! die end, L),
  U1 = Time1  * 1000/N,
  U2 = Time2  * 1000/N,
  io:format("spawning ~p processes took an avg of  ~p microseconds/process of CPU time and ~p of elapsed (wall_clock) time", [N, U1,U2]).

