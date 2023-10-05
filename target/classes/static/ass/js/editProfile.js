var editProfileButton = document.getElementById("edit-profile-button");
var editProfileModal = document.getElementById("edit-profile-modal");

editProfileButton.addEventListener("click", function() {
  editProfileModal.style.display = "block";
});

// When the user clicks the close button or outside the modal, close the modal
var closeButtons = document.getElementsByClassName("close");
window.onclick = function(event) {
  if (event.target === editProfileModal || event.target.classList.contains("close")) {
    editProfileModal.style.display = "none";
  }
};
