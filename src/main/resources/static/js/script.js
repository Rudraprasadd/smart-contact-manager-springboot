console.log("script loaded");

let currentTheme=getTheme();

document.addEventListener("DOMContentLoaded", () =>{
    changeTheme();
});
//



//to do
function changeTheme(){
    changepagetheme(currentTheme,currentTheme);
    document.querySelector('html').classList.add(currentTheme);
    //listener
    const themebutton=document.querySelector('#theme');
    themebutton.querySelector("span").textContent=
        currentTheme== "light" ? "Dark" : "Light";


    
    themebutton.addEventListener("click", (event) => {
        let oldtheme=currentTheme;
        console.log("theme button clicked!");
        if(currentTheme==="dark"){
            //theme light
            currentTheme='light';
        }
        else{
            //theme dark
            currentTheme='dark';
        }
        changepagetheme(currentTheme,oldtheme);
    });
}

//set theme to localstorage
function setTheme(theme){
    localStorage.setItem("theme",theme)
}

//getting theme from local storage
function getTheme(){
    let theme=localStorage.getItem("theme")
    return theme?theme:"light";
}

function changepagetheme(theme,oldtheme){
    setTheme(currentTheme);
        document.querySelector('html').classList.remove(oldtheme);
        document.querySelector('html').classList.add(theme);

        //text name 
        document.querySelector("#theme").querySelector("span").textContent=theme == "light" ? "Dark" : "Light";
}