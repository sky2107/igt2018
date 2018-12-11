####Dockerized PostgreSQL

Start the PostgreSQL database dockerized:

    docker run --name my_postgres -e POSTGRES_PASSWORD=jenskohler -d -p 5432:5432 postgres


Start the PGAdmin web-interface dockerized:

    docker run -p 80:80 -e "PGADMIN_DEFAULT_EMAIL=user@domain.com" -e "PGADMIN_DEFAULT_PASSWORD=jenskohler" --link my_postgres:my_postgres -d --name mypgadmin dpage/pgadmin4


To connect both postgresql Docker instances, --link <container-name> is used. 
Thus, in the pgadmin webinterface the connection must be <container-name> instead of <localhost>.

####Transaction Isolation Levels

Transaction Isolation Level is set via persistence.xml

     <!--    <property name="hibernate.connection.isolation" value="SERIALIZABLE" /> -->
Gives a concurrent TA exception with 2 concurrent TAs.     

     <!--  <property name="hibernate.connection.isolation" value="READ_COMMITTED" /> -->
Works with concurrent TAs.


Note: every DB vendor implements the isolation levels slightly different. 


PostgreSQL Doku:

In PostgreSQL, you can request any of the four standard transaction isolation levels. 
But internally, there are only three distinct isolation levels, which correspond to the 
levels Read Committed, Repeatable Read, and Serializable. When you select the level 
Read Uncommitted you really get Read Committed, and phantom reads are not possible in 
the PostgreSQL implementation of Repeatable Read, so the actual isolation level might 
be stricter than what you select. This is permitted by the SQL standard: the four 
isolation levels only define which phenomena must not happen, they do not define 
which phenomena must happen. The reason that PostgreSQL only provides three isolation 
levels is that this is the only sensible way to map the standard isolation levels to the 
multiversion concurrency control architecture. The behavior of the available isolation 
levels is detailed in the following subsections.