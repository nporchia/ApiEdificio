import React, { useState } from "react";

const ImageCarrousel = ({ visible, images, current, onClose }) => {

    const handleNext = () => {
        if (current.currentImage < images.length - 1) {
            current.setCurrentImage(current.currentImage + 1);
        } else {
            current.setCurrentImage(0);
        }
    };
    
    const handlePrev = () => {
        if (current.currentImage > 0) {
            current.setCurrentImage(current.currentImage - 1);
        } else {
            current.setCurrentImage(images.length - 1);
        }
    };

    return (
        <div className={`w-full h-screen fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-black bg-opacity-70 z-50 transition-opacity ${visible ? 'opacity-100 visible' : 'opacity-0 invisible'}`}>
       
                <div className="flex justify-center ">
                    <button onClick={handlePrev} className="fixed xs:py-2 xs:px-4 z-10 top-1/2 left-4 text-white bg-gray-800 md:p-6 md:px-8 text-2xl hover:bg-gray-700 rounded-full transform -translate-y-1/2">{'<'}</button>
                    <img src={images[current.currentImage]} alt="Imagen de reclamo" className="md:p-36 xs:px-20  xs:w-full absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"/>
                    <button onClick={handleNext} className="fixed z-10 xs:py-2 xs:px-4  top-1/2 right-4 text-white bg-gray-800 md:p-6 md:px-8 text-2xl hover:bg-gray-700 rounded-full transform -translate-y-1/2">{'>'}</button>
                </div>
                
                <button onClick={onClose} className="absolute xs:px-4 xs:py-2 top-2 right-4 text-white bg-gray-800 hover:bg-gray-700 p-6 px-8 text-2xl rounded-full">&times;</button>
            </div>
    );
}

export default ImageCarrousel;

