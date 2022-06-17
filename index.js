const express = require("express");
const mongoose = require("mongoose");
const morgan = require("morgan");
const cors = require("cors");
const CategoryRoutes = require("./routes/category");
const JobRoutes = require("./routes/job");
const UserRoutes = require("./routes/user");
const emailRoutes = require("./routes/email");
const candidateRoutes = require("./routes/candidate");
const authJwt = require("./helpers/jwt-auth");
const isAuthenticated = require("./helpers/isAuthenticated");
require("dotenv").config();
const app = express();
//DB Conncetion
mongoose
  .connect(process.env.CONNECTIONURL2)
  .then(() => {
    console.log("Connected to DB");
  })
  .catch((error) => {
    console.log(error);
  });

app.use(express.json());
app.use(cors("**"));
app.use(morgan("tiny"));
// app.use(authJwt());
// app.use(isAuthenticated);

//Basic Route
app.get("/", (req, res) => {
  res.send({ Message: "Ynot Careers DB", DataType: "JSON", DbUsed: "MongoDB" });
});
const api = process.env.apiUrl;
//Application specific Routes
app.use(`${api}/categories`, CategoryRoutes);
app.use(`${api}/jobs`, JobRoutes);
app.use(`${api}/users`, UserRoutes);
app.use(`${api}/email`, emailRoutes);
app.use(`${api}/candidates`, candidateRoutes);

//Starting Backend
app.listen(process.env.PORT || 3000, () => {
  console.log(`Backend Listening on ${process.env.PORT}`);
});
