"use strict"

function arrayDataForSubmit(newData, existingData, fieldName) {
    let newDataWithId = [];
    if (!jQuery.isEmptyObject(newData)) {
        for (let newDatum of newData) {
            for (let existingDatum of existingData) {
                if (existingDatum[fieldName] === newDatum[fieldName]) {
                    newDatum["id"] = existingDatum.id;
                }
            }
        }
        newDataWithId = newData.slice(0);
    }
    return newDataWithId;
};

function serializeLogic(value, name, object, arrayField) {
    if (object[name]) {
        if (!object[name].push) {
            object[name] = [object[name]];
        }
        object[name].push(value);
    }
    else if (arrayField) {
        object[name] = [];
        object[name].push(value);
    }
    else {
        object[name] = value;
    }
};

$.fn.serializeObject = function () {
    var serializedObject = {};
    var serializedArray = this.serializeArray();
    $.each(serializedArray, function () {
        if (this.name.indexOf("[") != -1) {
            let adjustedString = this.name.replace(/]/g, "").split("[");
            let obj = {};
            obj[adjustedString[1]] = this.value || "";
            serializeLogic(obj, adjustedString[0], serializedObject, true);
        }
        else {
            serializeLogic(this.value || "", this.name, serializedObject, false);
        }
    });
    return serializedObject;
};