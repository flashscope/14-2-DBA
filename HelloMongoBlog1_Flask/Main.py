from bson.objectid import ObjectId
from flask import Flask, request, render_template, redirect, url_for
from flask.ext.mongokit import MongoKit, Connection, Document

# configuration
MONGODB_HOST = 'localhost'
MONGODB_PORT = 27017

app = Flask(__name__)
db = MongoKit(app)


connection = Connection(app.config['MONGODB_HOST'], app.config['MONGODB_PORT'])
collection = connection['mydb'].blog

@app.route("/")
def helloIndex():
	return showPage();

@app.route("/insert", methods=["GET", "POST"])
def helloInsert():
	if request.method == 'POST':
		title = request.form['inputTitle']
		content = request.form['textContent']
		page = {"title":title,"content":content}
		collection.insert(page)
	return showPage();


@app.route("/modify", methods=["GET", "POST"])
def helloModify():
	if request.method == 'POST':
		pageID = request.form['pageID']
		collection.find_and_modify({"_id": ObjectId(pageID)},{"title":"modifiedTitle","content":"modifiedContent"})
		print "update"
	return showPage();


@app.route("/delete", methods=["GET", "POST"])
def helloDelete():
	if request.method == 'POST':
		pageID = request.form['pageID']
		delete = {"_id": ObjectId(pageID)}
		collection.remove(delete)
		print "delete"
	return showPage();

def showPage():
	pages = list(collection.find())
	print pages;
	return render_template('index.html', pagesData = pages);

if __name__ == "__main__":
	app.run(debug=True, host='0.0.0.0', port=5004);
