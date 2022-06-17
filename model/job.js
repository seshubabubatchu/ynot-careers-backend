const mongoose = require("mongoose");
const jobSchema = mongoose.Schema({
  title: {
    type: String,
    required: true,
  },
  category: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Category",
    required: true,
  },
  location: {
    type: String,
    required: true,
  },
  postedDate: {
    type: Date,
    default: Date.now,
  },
  description: {
    type: String,
    required: true,
  },
  descriptionTeaser: {
    type: String,
    required: true,
  },
  hiringType: {
    type: String,
    required: true,
  },
  display: {
    type: Boolean,
    required: true,
    default: false,
  },
});
jobSchema.virtual("id").get(function () {
  return this._id.toHexString();
});
jobSchema.set("toJSON", {
  virtuals: true,
});
jobSchema.index({ display: true });
exports.Job = mongoose.model("Job", jobSchema);
