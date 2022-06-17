const express = require("express");
const { Category } = require("../model/category");
const mongoose = require("mongoose");
const isAuthenticated = require("../helpers/isAuthenticated");
const router = express.Router();
router.get("/", async (req, res) => {
  try {
    const allCategories = await Category.find();
    if (!allCategories) return res.status(500).send("Cannot Fetch Categories");
    return res.status(200).send(allCategories);
  } catch (error) {
    console.log(error);
  }
});
router.get("/:id", async (req, res) => {
  try {
    const validCategoryId = mongoose.Types.ObjectId.isValid(req.params.id);
    if (validCategoryId) {
      let category = await Category.findById(req.params.id);
      if (!category) return res.status(404).send("Category Not Found");
      return res.status(200).send(category);
    } else {
      return res.status(400).send("Improper Id");
    }
  } catch (error) {
    console.log(error);
  }
});
router.post("/", isAuthenticated, async (req, res) => {
  try {
    let category = new Category({
      name: req.body.name,
    });
    category = await category.save();
    if (!category) return res.status(500).send("Error CreatingCategories");
    res.status(201).send(category);
  } catch (error) {
    console.log(error);
  }
});
router.put("/:id", isAuthenticated, async (req, res) => {
  try {
    let category = await Category.findByIdAndUpdate(
      req.params.id,
      {
        name: req.body.name,
      },
      { new: true }
    );
    if (!category) return res.status(500).send("Error UpdatingCategory");
    res.status(201).send(category);
  } catch (error) {
    console.log(error);
  }
});
router.delete("/:id", isAuthenticated, async (req, res) => {
  try {
    let category = await Category.findByIdAndDelete(req.params.id);
    if (!category) return res.status(500).send("Error Deleting Cateogry");
    res.status(200).send(category);
  } catch (error) {
    console.log(error);
  }
});
module.exports = router;
