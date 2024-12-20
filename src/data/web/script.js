

const qcm = "qcm";
const liaison = "liaison";
const elimination = "elimination";

const tresFacile = "très facile";
const facile = "facile";
const moyen = "moyen";
const difficile = "difficile";

shuffle(questions); //Appel d'une méthode qui mélange les questions entre elles à chaque redémarrage    



// Fonction permettant de convertir time in mm:ss to seconds
function convertTimeToSeconds(timeString) {
    const temp = timeString.split(':').map(Number);
    let minutes = temp[0];
    let seconds = temp[1];
    return ((minutes * 60) + seconds);
}

//fonction permettant d'afficher le temps total au début du questionnaire
function convertSecondsToTime(seconds) {
    let minutes = Math.floor(seconds / 60);
    let sec = seconds % 60;
    return `${minutes}m${sec}s`;
}

//Convertir tous les temps de questions en seconde à partir d'un string
questions.forEach(question => {
    if (typeof question.time === 'string' && question.time.includes(':')) {
        question.time = convertTimeToSeconds(question.time);
    }
});



//Relier avec les élements de la page html
const questionTitle = document.getElementById("question-title");
const answersContainer = document.getElementById("answers");
const progressBar = document.getElementById("progress-bar");
const timerElement = document.getElementById("timer");
const nextButton = document.getElementById("next-question");
const prevButton = document.getElementById("previous");
const resultSection = document.getElementById("result");
const affichagePoints = document.getElementById("points");
const correctAnswerContainer = document.getElementById("correct-answer");
const explanationElement = document.getElementById("explanation");
const confirmButton = document.getElementById("confirm");
const endPage = document.getElementById("end-page");
const finalScoreElement = document.getElementById("final-score");
const restartButton = document.getElementById("restart-quiz");
const difficulte = document.getElementById("difficulty-label");
const indicDifficulte = document.getElementById("difficulty-indicator");
const labelNbQuestion = document.getElementById("nbquestion-label");
const answerResult = document.getElementById("goodrep");
const header = document.querySelector('header');
const footer = document.querySelector('footer');
const card = document.getElementsByClassName('card');
const resultTitle = document.getElementById("result-title");
const texteTypeQuestion = document.getElementById("type-question");    
const elimButton = document.querySelectorAll(".elim-button")[0]; 
const imgQuestionContainer = document.getElementById("image-container");
const timeInfo = document.getElementById("time");
const progressBarQuestion = document.getElementById("progress-bar-question");
const iconQuestion = document.getElementById("question-icon");
const timeDiv = document.getElementsByClassName("time");
const startQuestionnaire = document.getElementById("start-quest");
const dureeNb = document.getElementById("infos");
const titre = document.getElementById("title-accueil");
const accueil = document.getElementsByClassName("accueil-card");
const timerImg = document.getElementById("timer-img");

//Instanciation d'éléments utilitaires
let currentQuestion = 0;
let score = 0;
let timeMax = 0;
let numQuestionMax = 0;
let registeredAnswers = new Array(questions.length);
let ptsCetteQuestion = new Array(questions.length).fill(0.0);
let selectedAnswerIndex = null;
let selectedAnswersIndex = [];
let totalPoints = 0;
let tempsTotal = 0;

//Calculer le total des points
for (let i = 0; i < questions.length; i++) {
    totalPoints += questions[i].points;
}

questions.forEach(question => {
    tempsTotal += question.time;
});

tempsTotal = convertSecondsToTime(tempsTotal);

/* --------- */
/* Fonctions */
/* --------- */

//Fonction pour afficher l'écran d'accueil
function displayAccueil() {
    card[0].style.display = "none";
    header.style.display = "none";
    document.querySelector("footer").style.display = "none";
    
    titre.textContent   = ressource;
    dureeNb.textContent = questions.length + " questions" + " - " + tempsTotal;
}

//
document.addEventListener("DOMContentLoaded", () => {
    startQuestionnaire.addEventListener("click", demarrerQuestionnaire);
});

function demarrerQuestionnaire() {
    header.style.display = "flex";
    footer.style.display = "flex";
    card[0].style.display = "block";
    startQuestionnaire.classList.add("hidden");
    accueil[0].style.display = "none";

    loadQuestion();
    confirmButton.addEventListener("click", handleAnswerValidation);
    nextButton.addEventListener("click", goToNextQuestion);
    prevButton.addEventListener("click", goToPreviousQuestion);
    restartButton.addEventListener("click", restartQuiz);
    elimButton.addEventListener("click", Elimination);
}

function loadQuestion() {

    updateProgressQuestionBar();
    
    let question = questions[currentQuestion];

    correctAnswerContainer.innerHTML = "";
    const explanationParagraph = document.createElement("p");
    explanationParagraph.id = "explanation";
    correctAnswerContainer.appendChild(explanationParagraph);


    imgQuestionContainer.innerHTML = "";

	timeMax = question.time;
    questionTitle.textContent = question.title;

    clearInterval(timerInterval);
    progressBar.style.width = "100%";
	confirmButton.classList.remove("hidden");
	
    labelNbQuestion.textContent = `Q${currentQuestion + 1} / Q${questions.length}`;
    document.title = `Questionnaire (${currentQuestion + 1}/${questions.length})`;

    mettreAJourTypeQuestion();

    //Reset boutons
    answersContainer.innerHTML = "";

    resultSection.classList.add("hidden");
    confirmButton.disabled = true;
    selectedAnswerIndex = null;
    selectedAnswersIndex = [];
    nextButton.disabled = true;
    prevButton.disabled = true;
	elimButton.classList.add("hidden");
    elimButton.disabled = false;
	//explainButton.classList.add("hidden");


    //Afficher la question
    if (question.type === liaison) {
        displayLiaisonQuestion(question);
    } else if (question.type === elimination) {
        displayElimQuestion(question);
    } else if (question.type === qcm) {
        displayRegularQuestion(question);
    }

    //Démarrer le timer
    if (timeMax !== 0 && numQuestionMax <= currentQuestion) startTimer();
    else {
        timerElement.textContent = "Ø";
    }

    
    timerImg.classList.add("hidden");
    if (currentQuestion + 1 < questions.length && questions[currentQuestion + 1].time !== null && questions[currentQuestion + 1].time !== 0) {
        timerImg.classList.remove("hidden");
    }
}

/* ---------------------------- */
/* Affichage des questionnaires */
/* ---------------------------- */

function displayRegularQuestion(question) {
    questionTitle.textContent = question.title;

    if (question.img != null) {
        const img = document.createElement("img");
        img.src = question.img;
        img.alt = "Image";
        img.classList.add("img-question");
        imgQuestionContainer.appendChild(img);
    }

    //Icone
    if (question.reponse_multiple) {
        iconQuestion.src = "assets/multi_icon.png";
    } else {
        iconQuestion.src = "assets/radio_icon.png";
    }

    // Effacer les réponses précédentes
    answersContainer.innerHTML = "";
    
    //Actualiser le nombre de colonnes
    answersContainer.style.gridTemplateColumns = "repeat(2, 1fr)";

    // Ajouter chaque réponse sous forme de bouton
    question.answers.forEach((answer, index) => {
        const button = document.createElement("button");
        button.classList.add("answer");
        button.textContent = "";
        
        const boxButton = document.createElement("img");

        if (question.reponse_multiple) {
            boxButton.src = "assets/multi_unchecked.png";
        } else {
            boxButton.src = "assets/radio_unchecked.png";        
        }
        
        boxButton.alt = "Image";
        boxButton.classList.add("img-button");
        button.appendChild(boxButton);

        if (answer.img != null) {
            const img = document.createElement("img");
            img.src = answer.img;
            img.alt = "Image";
            img.classList.add("img-reponse");
            button.appendChild(img);
        }

        if (answer.text != null) { 
            const text = document.createElement("p");
            text.textContent = answer.text;
            text.classList.add("text-reponse");
            button.appendChild(text);
        }

        // Gérer la sélection d'une réponse
        button.addEventListener("click", () => handleAnswerSelection(index));

        answersContainer.appendChild(button);
    });

    // Réinitialiser la section des résultats
    resultSection.classList.add("hidden");
    confirmButton.disabled = true;
    selectedAnswerIndex = null;
    selectedAnswersIndex = [];

    mettreAJourDifficulte(question);

    mettreAJourTypeQuestion();
    
        // Réinitialiser le timer
    if (numQuestionMax <= currentQuestion) {
        clearInterval(timerInterval);
        timeLeft = question.time;

        timerElement.textContent = `${timeLeft.toFixed(0)}s`;
        progressBar.style.width = "100%";
    }
    else if (question.time > 0) {
            startTimer();
        } else {
            timerElement.textContent = "Ø";
        }


    //console.log("NumQuestionMax : " + numQuestionMax + " CurrentQuestion : " + currentQuestion);
    // Rétablir la réponse enregistrée
    if (numQuestionMax > currentQuestion) {
        //console.log("Réponse chargée");
        
        if (question.reponse_multiple) {
            selectedAnswersIndex = registeredAnswers[currentQuestion];
            selectedAnswersIndex.forEach((index) => {
                answersContainer.children[index].classList.add("selected");
            });
        } else {
            selectedAnswerIndex = registeredAnswers[currentQuestion];
            if (selectedAnswerIndex !== null) {
                if (selectedAnswerIndex !== null && selectedAnswerIndex < answersContainer.children.length) {
                    answersContainer.children[selectedAnswerIndex].classList.add("selected");
                }
            }
        }

        handleAnswerValidation();
    }

    if(exam) prevButton.disabled = true;
}

function displayElimQuestion(question) {    
    // Afficher le titre de la question
    questionTitle.textContent = question.title;

    // Effacer les réponses précédentes
    answersContainer.innerHTML = "";

    //Actualiser le nombre de colonnes
    answersContainer.style.gridTemplateColumns = "repeat(2, 1fr)";

    // Réinitialiser les variables
    elimUseAmount = 0;
    selectedAnswerIndex = null;

    //Icone
    iconQuestion.src = "assets/elimination_icon.png";

    if (question.img != null) {
        const img = document.createElement("img");
        img.src = question.img;
        img.alt = "Image";
        img.classList.add("img-question");
        imgQuestionContainer.appendChild(img);
    }

    // Effacer les réponses précédentes
    answersContainer.innerHTML = "";

    // Ajouter chaque réponse sous forme de bouton
    question.answers.forEach((answer, index) => {
        const button = document.createElement("button");
        
        button.classList.add("answer");
        button.textContent = "";


        const boxButton = document.createElement("img");
        boxButton.src = "assets/radio_unchecked.png";
        boxButton.alt = "Image";
        boxButton.classList.add("img-button");
        button.appendChild(boxButton);


        if (answer.img != null) {
            const img = document.createElement("img");
            img.src = answer.img;
            img.alt = "Image";
            img.classList.add("img-reponse");
            button.appendChild(img);
        }
        
        if (answer.text != null) { 
            const text = document.createElement("p");
            text.textContent = answer.text;
            text.classList.add("text-reponse");
            button.appendChild(text);
        }

        // Désactiver si la réponse a été éliminée
        if (answer.disabled) {
            button.disabled = true;
            button.classList.add("disabled");
        }

        // Ajouter un gestionnaire de clic
        button.addEventListener("click", () => handleAnswerSelection(index));
        answersContainer.appendChild(button);
    });

    //Initiation du bouton elimButton
    //console.log(elimButton);
	elimButton.classList.remove("hidden");
    question.answers.forEach((answer, index) => {
        if (elimUseAmount+1 == answer.ordre)
            elimButton.textContent = "Élimination (-"+question.answers[index].points+")";
    });


    mettreAJourDifficulte(question);

    if (question.time > 0) {
        startTimer();
    } else {
        timerElement.textContent = "Ø";
    }

    if (numQuestionMax > currentQuestion) {
        
        selectedAnswerIndex = registeredAnswers[currentQuestion];
        if (selectedAnswerIndex !== null) {
            if (selectedAnswerIndex < answersContainer.children.length) {
                answersContainer.children[selectedAnswerIndex].classList.add("selected");
            }
        }

        handleAnswerValidation();
    }
}

// Modification de la fonction displayLiaisonQuestion
function displayLiaisonQuestion() {

    let question = questions[currentQuestion];

    // Mélanger indépendamment les colonnes gauche et droite
    const shuffledLeft = question.pairs.map(pair => pair.left).sort(() => Math.random() - 0.5);
    const shuffledRight = question.pairs.map(pair => pair.right).sort(() => Math.random() - 0.5);

    // Afficher le titre de la question
    questionTitle.textContent = question.title;

    // Actualiser le nombre de colonnes
    answersContainer.style.gridTemplateColumns = "repeat(3, 1fr)";

    //Icone
    iconQuestion.src = "assets/association_icon.png";

    // Ajouter les boutons pour chaque élément des colonnes
    answersContainer.innerHTML = "";
    shuffledLeft.forEach((leftText) => {
        const leftButton = document.createElement("button");
        leftButton.classList.add("answer");
        leftButton.textContent = leftText;

        const spacer = document.createElement("span");
        spacer.textContent = "";

        const rightText = shuffledRight.pop(); // Prendre un élément de la colonne droite mélangée
        const rightButton = document.createElement("button");
        rightButton.classList.add("answer");
        rightButton.textContent = rightText;

        // Gestion des sélections
        leftButton.addEventListener("click", () => handleLiaisonSelection(leftText, "left"));
        rightButton.addEventListener("click", () => handleLiaisonSelection(rightText, "right"));

        answersContainer.appendChild(leftButton);
        answersContainer.appendChild(spacer);
        answersContainer.appendChild(rightButton);
    });

    
    if (numQuestionMax > currentQuestion) {
        //console.log("Réponse chargée");

        console.log(registeredAnswers[currentQuestion]);
        if (registeredAnswers[currentQuestion] == -1) {
            handleLiaisonFin("timeOut");
        } else {
        registeredAnswers[currentQuestion].forEach((pair) => {
            handleLiaisonValidation(pair.left, pair.right);
        });
    }
    }
    mettreAJourDifficulte(question);
}



/* --------------------- */
/* Affichage du résultat */
/* --------------------- */

function afficheResultat() {
    //console.log("Affichage du résultat");
    //console.log(questions[currentQuestion]);
    //console.log("Réponse correcte : " + estCorrect());


    if(questions[currentQuestion].reponse_multiple) {
        answerResult.textContent = "Les bonnes réponses étaient";
        if (estCorrect()) {
            resultTitle.textContent = "Bonnes réponses";
            resultTitle.style.color = "green";
            affichagePoints.textContent = `+${ptsCetteQuestion[currentQuestion]} points`;
            confirmButton.disabled = true;
        } else {
            resultTitle.textContent = "Mauvaise réponse";
            resultTitle.style.color = "red";
            affichagePoints.textContent = "+0 point";
            confirmButton.disabled = true;
        }
    } else {
        answerResult.textContent = "La bonne réponse était";
        //console.log("Affichage du résultat 2");

        if (estCorrect()) {
            resultTitle.textContent = "Bonne réponse";
            resultTitle.style.color = "green";
            affichagePoints.textContent = `+${ptsCetteQuestion[currentQuestion]} points`;
            confirmButton.disabled = true;
        } else {
            resultTitle.textContent = "Mauvaise réponse";
            resultTitle.style.color = "red";
            affichagePoints.textContent = "+0 point";
            confirmButton.disabled = true;
        }
    }

    correctAnswerContainer.textContent = getCorrectAnswer();
    
    explanationElement.textContent = questions[currentQuestion].explanation;

    resultSection.classList.remove("hidden");
    nextButton.disabled = false;
    if (!exam) prevButton.disabled = currentQuestion === 0;
}

function goToNextQuestion() {
    if (currentQuestion < questions.length - 1) {
        currentQuestion++;
        loadQuestion();
    } else {
        showEndPage();
    }
}

function goToPreviousQuestion() {
    if (currentQuestion > 0) {
        currentQuestion--;
        loadQuestion();
    }
}

/* ----------------------------------- */
/* Gérer la sélection et la validation */
/* ------------------------------------*/

function handleAnswerSelection(index) {
    const buttons = document.querySelectorAll(".answer");
    const selectedAnswer = questions[currentQuestion].answers[index];
    const question = questions[currentQuestion];


    if (questions[currentQuestion].reponse_multiple) {
        if (buttons[index].classList.contains("selected")) {
            buttons[index].classList.remove("selected");
            selectedAnswersIndex.splice(selectedAnswersIndex.indexOf(index), 1);
        } else {
            buttons[index].classList.add("selected");
            selectedAnswersIndex.push(index);
        }
        
        confirmButton.disabled = true;
        
        if (selectedAnswersIndex.length > 0) {
            confirmButton.disabled = false;
        }

    }
    else {
        // Réinitialiser les styles des boutons
        buttons.forEach((button) => {
            button.classList.remove("selected");
        });

        // Ajouter la classe "selected"
        buttons[index].classList.add("selected");
        selectedAnswerIndex = index;

        // Activer le bouton de validation
        confirmButton.disabled = false;
    }

    
    if (questions[currentQuestion].type == elimination) {
        buttons.forEach((button) => {
            if (button.classList.contains("selected")) {
                const img = button.querySelector(".img-button");
                img.src = "assets/radio_checked.png";
            } else {
                const img = button.querySelector(".img-button");
                img.src = "assets/radio_unchecked.png";
            }
        });
    } else 
    if (questions[currentQuestion].type == qcm) {
        if (questions[currentQuestion].reponse_multiple) {
            buttons.forEach((button) => {
                if (button.classList.contains("selected")) {
                    const img = button.querySelector(".img-button");
                    img.src = "assets/multi_checked.png";
                } else {
                    const img = button.querySelector(".img-button");
                    img.src = "assets/multi_unchecked.png";
                }
            });
        }
        else{
            buttons.forEach((button) => {
                if (button.classList.contains("selected")) {
                    const img = button.querySelector(".img-button");
                    img.src = "assets/radio_checked.png";
                } else {
                    const img = button.querySelector(".img-button");
                    img.src = "assets/radio_unchecked.png";
                }
            });
        }
    }
}


function handleAnswerValidation() {
    //console.log("Validation de la réponse");

    const question = questions[currentQuestion];
    const buttons = document.querySelectorAll(".answer");

    // Arrêter le timer
    clearInterval(timerInterval);

    // Vérifier la réponse sélectionnée
    buttons.forEach((button, idx) => {
        const answer = question.answers[idx];
        if (answer.correct) {
            button.classList.add("correct");
        } else if (idx === selectedAnswerIndex) {
            button.classList.add("incorrect");
        }
        button.disabled = true;
    });

    //console.log("NumQuestionMax (pré): " + numQuestionMax);
    if (numQuestionMax <= currentQuestion) {

        //Sauvegarde de la réponse
        if (question.reponse_multiple) {
            registeredAnswers[currentQuestion] = selectedAnswersIndex;
            //console.log("Réponse enregistrée : " + selectedAnswersIndex);
        } else {
            registeredAnswers[currentQuestion] = selectedAnswerIndex;
            //console.log("Réponse enregistrée : " + selectedAnswerIndex);
        }

        if (estCorrect()) {
            ptsCetteQuestion[currentQuestion] += question.points;
            score += ptsCetteQuestion[currentQuestion];
        }
        numQuestionMax++;
    }
    //console.log("NumQuestionMax : " + numQuestionMax);


    elimButton.disabled = true;
    afficheResultat();
}

// Nouvelle fonction pour gérer les sélections gauche/droite
let selectedLeft = null;
let selectedRight = null;

function handleLiaisonSelection(text, side) {
    const buttons = answersContainer.querySelectorAll(".answer");

    if (side === "left") {
        // Réinitialiser les sélections de gauche
        buttons.forEach((btn) => btn.classList.remove("selected"));
        selectedLeft = text;
        buttons.forEach((btn) => {
            if (btn.textContent === text) btn.classList.add("selected");
        });
    } else if (side === "right") {
        // Réinitialiser les sélections de droite
        buttons.forEach((btn) => btn.classList.remove("selected"));
        selectedRight = text;
        buttons.forEach((btn) => {
            if (btn.textContent === text) btn.classList.add("selected");
        });
    }

    // Valider si les deux côtés sont sélectionnés
    if (selectedLeft && selectedRight) {
        handleLiaisonValidation(selectedLeft, selectedRight);
    }
}

function checkLiaisonValidation() {
    let question = questions[currentQuestion];
    const buttons = document.querySelectorAll(".answer");
    let indexBtnGauche = -1;
    let indexBtnDroite = -1;
    
    for (let i = 0; i < buttons.length; i += 2) {
        if (buttons[i].classList.contains("selected")) {
            indexBtnGauche = i;
        }
    }

    for (let i = 1; i < buttons.length; i += 2) {
        if (buttons[i].classList.contains("selected")) {
            indexBtnDroite = i;
        }
    }

    if (indexBtnGauche !== -1 && indexBtnDroite !== -1) {
        handleLiaisonValidation(indexBtnGauche, indexBtnDroite);
    }
}

// Modification de la fonction handleLiaisonValidation
function handleLiaisonValidation(leftText, rightText) {
    const question = questions[currentQuestion];
    const isCorrectPair = question.pairs.some(
        (pair) => pair.left === leftText && pair.right === rightText
    );

    const buttons = answersContainer.querySelectorAll(".answer");
    
    if (isCorrectPair) {
        // Marquer les boutons comme corrects
        buttons.forEach((button) => {
            if (button.textContent === leftText || button.textContent === rightText) {
                button.classList.add("correct");
                button.disabled = true;
                // Vérifier si tous les boutons sont corrects
                let allCorrect = true;
                buttons.forEach((btn) => {
                    if (!btn.classList.contains("correct")) {
                        allCorrect = false;
                    }
                });
                if (allCorrect) {
                    handleLiaisonFin("correct");
                }
            }
        });
    } else {
        // Marquer les boutons comme incorrects
        buttons.forEach((button) => {
            if (button.textContent === leftText || button.textContent === rightText) {
                button.classList.add("incorrect");
                button.disabled = true;
                handleLiaisonFin("incorrect");
            }
        });
    }

    // Enregistrer la réponse
    if (registeredAnswers[currentQuestion] == null) {
        registeredAnswers[currentQuestion] = new Array();
    }
    registeredAnswers[currentQuestion].push({ left: leftText, right: rightText });

    // Retirer la balise selected des boutons
    buttons.forEach((button) => button.classList.remove("selected"));
    // Réinitialiser les sélections
    selectedLeft = null;
    selectedRight = null;

    // Dessiner un trait entre les boutons sélectionnés
    const leftButton = Array.from(buttons).find(button => button.textContent === leftText);
    const rightButton = Array.from(buttons).find(button => button.textContent === rightText);

    if (leftButton && rightButton) {
        const line = document.createElement("div");
        line.classList.add("line");

        const leftRect = leftButton.getBoundingClientRect();
        const rightRect = rightButton.getBoundingClientRect();

        const leftCenterX = leftRect.left + leftRect.width / 2;
        const leftCenterY = leftRect.top + leftRect.height / 2;
        const rightCenterX = rightRect.left + rightRect.width / 2;
        const rightCenterY = rightRect.top + rightRect.height / 2;

        const length = Math.sqrt(Math.pow(rightCenterX - leftCenterX, 2) + Math.pow(rightCenterY - leftCenterY, 2));
        const angle = Math.atan2(rightCenterY - leftCenterY, rightCenterX - leftCenterX) * 180 / Math.PI;

        line.style.width = `${length}px`;
        line.style.transform = `rotate(${angle}deg)`;
        line.style.position = 'absolute';
        line.style.top = `${leftCenterY}px`;
        line.style.left = `${leftCenterX}px`;
        line.style.transformOrigin = '0 0';
        line.style.border = '1px solid black';

        answersContainer.appendChild(line);

        // Rendre la ligne responsive
        window.addEventListener('resize', () => {
            const newLeftRect = leftButton.getBoundingClientRect();
            const newRightRect = rightButton.getBoundingClientRect();

            const newLeftCenterX = newLeftRect.left + newLeftRect.width / 2;
            const newLeftCenterY = newLeftRect.top + newLeftRect.height / 2;
            const newRightCenterX = newRightRect.left + newRightRect.width / 2;
            const newRightCenterY = newRightRect.top + newRightRect.height / 2;

            const newLength = Math.sqrt(Math.pow(newRightCenterX - newLeftCenterX, 2) + Math.pow(newRightCenterY - newLeftCenterY, 2));
            const newAngle = Math.atan2(newRightCenterY - newLeftCenterY, newRightCenterX - newLeftCenterX) * 180 / Math.PI;

            line.style.width = `${newLength}px`;
            line.style.transform = `rotate(${newAngle}deg)`;
            line.style.top = `${newLeftCenterY}px`;
            line.style.left = `${newLeftCenterX}px`;
        });
    }
}


function handleLiaisonFin(type) {

    clearInterval(timerInterval);

	let correct;

    explanationElement.textContent = questions[currentQuestion].explanation;

    resultSection.classList.remove("hidden");
    nextButton.disabled = false;
    if (!exam) prevButton.disabled = currentQuestion === 0;


    //Désactiver tous les boutons
    const buttons = document.querySelectorAll(".answer");
    buttons.forEach((button) => {
        button.disabled = true;
        button.classList.remove("selected");
    });

    correctAnswerContainer.innerHTML = "";
    answerResult.textContent = "La bonne liaison était";
	for(let i=0;i<questions[currentQuestion].pairs.length; i++) {
        const explanationParagraph = document.createElement("p");
        explanationParagraph.id = "explanation";
        explanationParagraph.textContent = questions[currentQuestion].pairs[i].left + " <-> " + questions[currentQuestion].pairs[i].right;
        correctAnswerContainer.appendChild(explanationParagraph);
    }

    if (type == "timeOut") {
        console.log("Temps écoulé");
        resultTitle.textContent = "Temps écoulé !";
        resultTitle.style.color = "red";
        affichagePoints.textContent = "+0 points";
        if (numQuestionMax <= currentQuestion) {
            numQuestionMax++;
        }
        registeredAnswers[currentQuestion] = -1;
        return;
    } else if (type == "correct") {
        console.log("Bonne réponse");
        correct = true;
    } else {
        console.log("Mauvaise réponse");
    	correct = false;
    }
    

    if (numQuestionMax <= currentQuestion) {

        if (correct) {
            ptsCetteQuestion[currentQuestion] += questions[currentQuestion].points;
            score += ptsCetteQuestion[currentQuestion];
        }
        numQuestionMax++;
    }


    //Afficher le résultat final
    if (correct) {
        resultTitle.textContent = "Bonne réponse";
        resultTitle.style.color = "green";
        affichagePoints.textContent = `+${ptsCetteQuestion[currentQuestion]} points`;
        if (numQuestionMax <= currentQuestion) {
            score += ptsCetteQuestion[currentQuestion];
        }
    } else {
        resultTitle.textContent = "Mauvaise réponse";
        resultTitle.style.color = "red";
        affichagePoints.textContent = "+0 points";
    }
}

/* ----------------------------------------- */
/* Gérer les actions de fin du questionnaire */
/* ----------------------------------------- */

function showEndPage() {
    document.querySelector(".question-container").classList.add("hidden");
    resultSection.classList.add("hidden");

    footer.classList.add("hidden");
    footer.style.display = "none";

    header.classList.add("hidden");
    header.style.display = "none";
    
    endPage.classList.remove("hidden");
    finalScoreElement.textContent = `Votre score final est de ${score} points sur ${totalPoints}!`;
    
    const cardTemp = document.getElementById('card');
    cardTemp.style.display = "none";

    console.log("Fin du questionnaire : " + cardTemp);
    document.title = `Questionnaire (fin)`;
}

function restartQuiz() {
    currentQuestion = 0;
    score = 0;
    numQuestionMax=0;
    endPage.classList.add("hidden");

    footer.classList.remove("hidden");
    footer.style.display = "flex";

    header.classList.remove("hidden");
    header.style.display = "flex";

    document.querySelector(".question-container").classList.remove("hidden");
    loadQuestion();
    for (let i = 0; i < card.length; i++) {
        card[i].style.display = 'block';
    }
    
    const cardTemp = document.getElementById('card');
    cardTemp.classList.remove("hidden");
}

let timerInterval;

function startTimer() {
    clearInterval(timerInterval);
    timeLeft = timeMax; 
    const updateInterval = 10;
    const step = updateInterval / 1000;
    
    timerElement.textContent = `${timeLeft.toFixed(0)}s`;
    progressBar.style.width = "100%";

    timerInterval = setInterval(() => {
        timeLeft -= step;

        if (timeLeft <= 0) {
            timeLeft = 0;
            clearInterval(timerInterval);
            if (exam) handleTimeOut();
        }

        timerElement.textContent = `${timeLeft.toFixed(0)}s`;
        progressBar.style.width = `${(timeLeft / timeMax) * 100}%`;
    }, updateInterval);
}

/* -------------- */
/* Gérer le timer */
/* -------------- */

function handleTimeOut() {
    clearInterval(timerInterval);

    const question = questions[currentQuestion];
    const answerButtons = document.querySelectorAll(".answer"); 

    answerButtons.forEach((button) => {
        button.disabled = true; 
        if(question.type !== liaison) 
			answer = question.answers.find((a) => a.text === button.textContent);
		else
        {
			handleLiaisonFin("timeOut");
            return;
        }

        if (answer && answer.correct) {
            button.classList.add("correct");
        }
    });

    confirmButton.disabled = true;
    nextButton.disabled = false;

    resultTitle.textContent = "Temps écoulé !";
    resultTitle.style.color = "red";

	if(question.type !== liaison) 
    	correctAnswerContainer.textContent = question.answers.find((a) => a.correct).text;
	else
		handleLiaisonFin("timeOut");

    explanationElement.textContent = question.explanation;

    resultSection.classList.remove("hidden");
    affichagePoints.textContent = "+0 points";
    if (!exam) prevButton.disabled = currentQuestion === 0;
    
    
    //Sauvegarde de la réponse
        if (question.type !== liaison) {
            registeredAnswers[currentQuestion] = null;
    } else {
        registeredAnswers[currentQuestion] = -1;
    }
    
    numQuestionMax++;
}



/* ---------- */
/* Get et Set */
/* ---------- */

function getCorrectAnswer() {
    let correctAnswer = "";

    if (questions[currentQuestion].reponse_multiple) {
        for (let i = 0; i < questions[currentQuestion].answers.length; i++) {
            if (questions[currentQuestion].answers[i].correct) {
                if (correctAnswer !== "") {
                    correctAnswer += ", ";
                }
                correctAnswer += questions[currentQuestion].answers[i].text;
            }
        }
    } else {
        correctAnswer = questions[currentQuestion].text;
    }

    return correctAnswer;
}

function estCorrect() {

    let correct = true;
    const question = questions[currentQuestion];

    //Vérifier si la réponse n'est pas un time out précédent
    if (numQuestionMax > currentQuestion && registeredAnswers[currentQuestion] == null) {
        return false;
    }

    if (question.reponse_multiple) {
        if (selectedAnswersIndex.length !== question.answers.filter((a) => a.correct).length) {
            correct = false;
        }

        for (let i = 0; i < selectedAnswersIndex.length; i++) {
            if (!question.answers[selectedAnswersIndex[i]].correct) {
                correct = false;
            }
        }
    } else {
        return question.answers[selectedAnswerIndex].correct;
    }

    return correct;
}


function mettreAJourTypeQuestion() {
    if (questions[currentQuestion].type === liaison) {
        texteTypeQuestion.textContent = "Question par liaison";
    }
    else if (questions[currentQuestion].type === elimination) {
        texteTypeQuestion.textContent = "Question par élimination";
    }
    else if (questions[currentQuestion].reponse_multiple) {
        texteTypeQuestion.textContent = "Question à choix multiples";
    } else {
        texteTypeQuestion.textContent = "Question à choix unique";
    }
    texteTypeQuestion.textContent = texteTypeQuestion.textContent + " (" + questions[currentQuestion].points + " points)";
}

function mettreAJourDifficulte(question) {
    switch (question.difficulte) {
        case tresFacile:
            difficulte.textContent = tresFacile;
            indicDifficulte.style.backgroundColor = "green";
            card[0].style.boxShadow = "0 0 20px 10px rgba(0, 255, 0, 0.2)";
            break;
        case facile:
            difficulte.textContent = facile;
            indicDifficulte.style.backgroundColor = "yellow";
            card[0].style.boxShadow = "0 0 20px 10px rgba(255, 255, 0, 0.2)";
            break;
        case moyen:
            difficulte.textContent = moyen;
            indicDifficulte.style.backgroundColor = "red";
            card[0].style.boxShadow = "0 0 20px 10px rgba(255, 0, 0, 0.2)";
            break;
        case difficile:
            difficulte.textContent = difficile;
            indicDifficulte.style.backgroundColor = "black";
            card[0].style.boxShadow = "0 0 20px 10px rgba(255, 183, 183, 0.2)";
            break;
    }
}

/* ---------------------- */
/* Actions de Elimination */
/* ---------------------- */

function Elimination () {
    let question = questions[currentQuestion];
    elimUseAmount++;

    if (elimUseAmount > 2) return;

    const buttons = document.querySelectorAll(".answer");
    // Éliminer 2 mauvaises réponses
    question.answers.forEach((answer, index) => {
        if (!answer.correct && elimUseAmount == answer.ordre) {
            ptsCetteQuestion[currentQuestion] -= question.answers[index].points;
            buttons[index].disabled = true;
            buttons[index].classList.add("disabled");
        }
    });
    
    question.answers.forEach((answer, index) => {
        if(elimUseAmount + 1 == answer.ordre)
            elimButton.textContent = "Élimination (-"+question.answers[index].points+")";
    });

    if (elimUseAmount >= 2)
    {
        elimButton.disabled = true;
        elimButton.textContent = "Élimination";
    }

    //Déselectionner le bouton s'il est désactivé
    if (buttons[selectedAnswerIndex].disabled)
    {
        buttons[selectedAnswerIndex].classList.remove("selected");
        selectedAnswerIndex = null;
        confirmButton.disabled = true;
    }

    
    buttons.forEach((button) => {
        if (button.classList.contains("selected")) {
            const img = button.querySelector(".img-button");
            img.src = "assets/radio_checked.png";
        } else {
            const img = button.querySelector(".img-button");
            img.src = "assets/radio_unchecked.png";
        }
    });
}

//Ajout des event listener
document.addEventListener("DOMContentLoaded", () => {
    displayAccueil();
});

function updateProgressQuestionBar() {
    progressBarQuestion.style.width = `${((currentQuestion + 1) / questions.length) * 100}%`;
}

function shuffle(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
}