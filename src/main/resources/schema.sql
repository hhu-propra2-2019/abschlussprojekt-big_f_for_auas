drop table if exists datepoll;
create table datepoll (
    id integer unsigned auto_increment not null primary key,
    link text not null,
    lastmodified datetime,
    isTerminated boolean,
    description varchar(255),
    location varchar(255),
    prioritychoice boolean,
    anonymous boolean,
    openforownentries boolean,
    open boolean,
    singlechoice boolean,
    startdate datetime,
    enddate datetime,
    userid int unsigned not null,
    constraint "datepoll_db_constraint"
        foreign key (userid) references user(id)
);

drop table if exists datepolloption;
create table datepolloption (
    id integer unsigned auto_increment not null primary key ,
    datepollid integer unsigned not null,
    votes integer not null ,
    description text,
    startdate datetime,
    enddate datetime,
    constraint "datepolloption_db_constraint"
        foreign key (datepollid) references datepoll(id)
);

drop table if exists prioritychoice;
create table prioritychoice(
    id integer unsigned auto_increment not null primary key,
    datepolloptionid integer unsigned not null ,
    participantid integer unsigned not null,
    constraint "prioritychoice_db_constraint"
        foreign key (datepolloptionid) references datepolloption(id),
        foreign key (participantid) references participant(userid)

);

drop table if exists participant;
create table participant(
    userid integer unsigned auto_increment not null primary key,
    datepollid integer unsigned not null,
    OPEN boolean,
    REOPEN boolean,
    ongoing boolean,
    ended boolean,
    constraint "participant_db_constraint"
        foreign key (userid) references user(id),
        foreign key (datepollid) references datepoll(id)
);

drop table if exists user;
create table user(
    id integer unsigned auto_increment not null primary key
);