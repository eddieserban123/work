package main

import (
	"net/http"
	"log"
	"context"
	"github.com/gorilla/mux"
	"fmt"
	"database/sql"
	"flag"
)
import (
	_ "github.com/denisenkom/go-mssqldb"
	"time"
	"net/url"
)

var (
	debug         = flag.Bool("debug", true, "enable debugging")
	password      = flag.String("password", "puieMonta140!", "the database password")
	port     	  = flag.Int("port", 1433, "the database port")
	server        = flag.String("server", "192.168.170.3", "the database server")
	user          = flag.String("user", "sa", "the database user")
	database	  = flag.String("database", "TEST", "the database")
)

var db *sql.DB = nil


func ArticlesCategoryHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	w.WriteHeader(http.StatusOK)
	article := getArticleById(vars["category"])
	fmt.Fprintf(w, "Category: %v\n", article)
}

func main() {
	db = initSqlConnection()
	r := mux.NewRouter()
	r.HandleFunc("/articles/{category}/", ArticlesCategoryHandler)
	log.Fatal(http.ListenAndServe(":8000", r))
}

func initSqlConnection() *sql.DB {
	query := url.Values{}
	query.Add("database", fmt.Sprintf("%s", "TEST"))

	u := &url.URL{
		Scheme:   "sqlserver",
		User:     url.UserPassword("sa", "puieMonta140!"),
		Host:     fmt.Sprintf("%s:%d", "192.168.170.3", 1433),
		// Path:  instance, // if connecting to an instance instead of a port
		RawQuery: query.Encode(),
	}
	connectionString := u.String()
	fmt.Println(connectionString)
	db, err := sql.Open("mssql", connectionString)
	if err!= nil {
		log.Fatal(err)
	}

	ctx := context.Background()

	// Check if database is alive.
	err = db.PingContext(ctx)
	if err != nil {
		log.Fatal("Error pinging database: " + err.Error())
	}
	return db

}

func setUpConnection(conn *sql.DB) {
	conn.SetConnMaxLifetime(time.Minute * 5);
	conn.SetMaxIdleConns(0);
	conn.SetMaxOpenConns(2);
}

func getArticleById(id string) string {
	ctx := context.Background()

	// Check if database is alive.
	err := db.PingContext(ctx)
	if err != nil {
		log.Fatal("Error pinging database: " + err.Error())
	}
	rows, err := db.Query("select * from dbo.articles where id='1'")
	if err != nil  {
		log.Fatal("error retrieving columns for id :", err.Error(),id)
	}
	defer rows.Close()
	cols, err := rows.Columns()
	if err != nil || cols !=nil {
		log.Fatal("error retrieving columns for id :", err.Error(),id)
	}
	return "222"
}
