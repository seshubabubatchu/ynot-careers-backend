const express = require("express");
const { User } = require("../model/user");
const jwt = require("jsonwebtoken");
const router = express.Router();
const bcryptjs = require("bcryptjs");
const { default: mongoose } = require("mongoose");
const isAuthenticated = require("../helpers/isAuthenticated");
router.get("/", async (req, res) => {
  try {
    const allUsers = await User.find();
    if (!allUsers) return res.status(500).send("Cannot Fetch Users");
    return res.status(200).send(allUsers);
  } catch (error) {
    console.log(error);
  }
});
router.get("/:id", async (req, res) => {
  try {
    let user = await User.findById(req.params.id);
    if (!user) return res.status(500).send("Cannot Fetch User");
    return res.status(200).send(user);
  } catch (error) {
    console.log(error);
  }
});
router.post("/admin/register", isAuthenticated, async (req, res) => {
  try {
    if (req.headers.authorization) {
      const token = req.headers.authorization.split(" ")[1];
      const payload = jwt.verify(token, process.env.SECRET);
      if (payload.isAdmin) {
        let user = new User({
          name: req.body.name,
          email: req.body.email,
          passwordHash: bcryptjs.hashSync(req.body.password, 10),
          isAdmin: req.body.isAdmin,
        });
        user = await user.save();
        if (!user) return res.status(400).send("Error Creating a User");
        return res.status(201).send(user);
      }
    } else {
      return res.send("you dont have access to add admin or user");
    }
  } catch (error) {
    console.log(error);
  }
});
router.put("/admin/update/:id", isAuthenticated, async (req, res) => {
  console.log("hai admin update");
  try {
    if (req.headers.authorization) {
      const token = req.headers.authorization.split(" ")[1];
      const payload = jwt.verify(token, process.env.SECRET);
      const validId = mongoose.isValidObjectId(req.params.id);
      if (validId) {
        const userToUpdate = await User.findById(req.params.id);

        if (userToUpdate) {
          if (payload.isAdmin) {
            let user = await User.findByIdAndUpdate(
              req.params.id,
              {
                name: req.body.name,
                email: req.body.email,
                // passwordHash: bcryptjs.hashSync(req.body.password, 10),
                isAdmin: req.body.isAdmin,
              },
              { new: true }
            );
            if (!user) return res.status(400).send("error updating USer");
            return res.status(200).send(user);
          } else {
            return res.send("You dont have access to update user");
          }
        } else {
          return res.send("user not found");
        }
      } else {
        return res.send("Invalid USer Id");
      }
    } else {
      return res.send("no token");
    }
  } catch (error) {
    console.log(error);
  }
});
router.post("/register", async (req, res) => {
  try {
    let user = new User({
      name: req.body.name,
      email: req.body.email,
      passwordHash: bcryptjs.hashSync(req.body.password, 10),
    });
    user = await user.save();
    if (!user) return res.status(500).send("Error Creating User");
    res.status(201).send(user);
  } catch (error) {
    console.log(error);
  }
});
router.post("/login", async (req, res) => {
  try {
    // console.log(req.body);
    let user = await User.findOne({ email: req.body.email });
    if (user) {
      const validUser = bcryptjs.compareSync(
        req.body.password,
        user.passwordHash
      );
      if (validUser) {
        const token = jwt.sign(
          { id: user.id, email: user.email, isAdmin: user.isAdmin },
          process.env.SECRET,
          {
            expiresIn: "1d",
          }
        );
        // console.log(token);
        return res.status(200).send({ token: token });
      } else {
        return res.status(400).send("Invalid Credentails");
      }
    } else {
      return res.status(400).send("User Not Found");
    }
  } catch (error) {
    console.log(error);
  }
});
router.put("/:id", isAuthenticated, async (req, res) => {
  console.log(req.body);
  try {
    let user = await User.findByIdAndUpdate(
      req.params.id,
      {
        name: req.body.name,
        email: req.body.email,
        isAdmin: req.body.isAdmin,
        passwordHash: bcryptjs.hashSync(req.body.password, 10),
      },
      { new: true }
    );
    if (!user) return res.status(500).send("Error Updatinguser");
    res.status(201).send(user);
  } catch (error) {
    console.log(error);
  }
});
router.delete("/:id", async (req, res) => {
  try {
    let user = await User.findByIdAndDelete(req.params.id);
    if (!user) return res.status(500).send("Error Deleting Cateogry");
    res.status(200).send(user);
  } catch (error) {
    console.log(error);
  }
});

module.exports = router;
