const jwt = require("jsonwebtoken");
function isAuthenticated(req, res, next) {
  console.log("hai from backend");
  const token = req.headers.authorization;
  //   console.log(token);
  if (token) {
    const jwtToken = token.split(" ")[1];
    // console.log("Token is", jwtToken);
    const payload = jwt.verify(jwtToken, process.env.SECRET);
    // console.log("payload is", payload);
    // console.log(payload);
    // console.log("from backend auth", token);
    if (payload.isAdmin) {
      next();
    } else {
      return res
        .status(400)
        .send("Un Authorized to perform this action(from backend)");
    }
  } else {
    return res.status(400).send("Un Authorized(from backend)");
  }
}
module.exports = isAuthenticated;
