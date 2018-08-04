const functions = require("firebase-functions");

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require("firebase-admin");
admin.initializeApp();
const db = admin.firestore();

exports.updateRatings = functions.firestore
  .document("ratings/{ratingId}")
  .onCreate((snap, context) => {
    const newRating = snap.data();
    const ratingVal = newRating.rating;
    var beerRef = db.collection("beers").doc(newRating.beerId);

    // Update aggregations in a transaction
    return db.runTransaction(transaction => {
      return transaction.get(beerRef).then(beerDoc => {
        const beer = beerDoc.data();

        console.log("*** Running Transaction ***");
        console.log("beer: ", beer);
        var newNumRatings = beer.numRatings + 1;

        // Compute new average rating
        var oldRatingTotal = beer.avgRating * beer.numRatings;
        var newAvgRating = (oldRatingTotal + ratingVal) / newNumRatings;

        console.log("newNumRatings: ", newNumRatings);
        console.log("ratingVal: ", ratingVal);
        console.log("oldRatingTotal: ", oldRatingTotal);
        console.log("newAvgRating: ", newAvgRating);

        // Update restaurant info
        return transaction.update(beerRef, {
          avgRating: newAvgRating,
          numRatings: newNumRatings
        });
      });
    });
  });
