const expressJwt = require("express-jwt");

function authJwt() {
  const secret = process.env.SECRET;
  const api = process.env.apiUrl;
  return expressJwt({
    secret,
    algorithms: ["HS256"],
    isRevoked: isRevoked,
  }).unless({
    path: [
      { url: /\/api\/v1\/categories(.*)/, methods: ["GET", "OPTIONS"] },
      { url: /\/api\/v1\/jobs(.*)/, methods: ["GET", "OPTIONS"] },
      { url: /\/api\/v1\/users(.*)/, methods: ["GET", "OPTIONS"] },
      { url: /\/api\/v1\/email(.*)/, methods: ["GET", "OPTIONS"] },
      { url: /\/api\/v1\/candidates(.*)/, methods: ["GET", "OPTIONS"] },
      `${api}/users/login`,
      `${api}/users/register`,
      // { url: /(.*)/ },
    ],
  });
}

async function isRevoked(req, payload, done) {
  console.log("payload is", payload);
  if (!payload.isAdmin) {
    done(null, true);
    // done();
  }

  done();
}

module.exports = authJwt;
