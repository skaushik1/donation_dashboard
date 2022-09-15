package com.app.donateclaim.helper

import com.bumptech.glide.load.ImageHeaderParser


/**
 * This class is used for declaring constant values which ar used in app
 */
public class AppConstant {

    /**
     * This annotation is used for some default values which will be used in app
     */
    annotation class DefaultValues {
        companion object {
            const val DeviceType: String = "2" //1-Android, 0-iOS //todo confirm once API is ready
            var DeviceToken = "12345678"
        }
    }

    /**
     * This annotation class is used for Login type.
     */
    annotation class LoginType {
        companion object {
            const val Standard = "STANDARD"
            const val FaceBook = "FACEBOOK"
            const val Google = "GOOGLE"
            const val Apple = "APPLE"
        }
    }

    /**
     * This annotation class is used to define user type.
     */
    annotation class UserType {
        companion object {
            const val Customer = "CUSTOMER"
            const val Driver = "DRIVER"
        }
    }


    /**
     * This annotation class is used to define Trip Statues
     */
    annotation class TripStatues {
        companion object {
            const val ACCEPTED = "ACCEPTED"
            const val PENDING = "PENDING"
            const val COMPLETED = "COMPLETED"
        }
    }



    /**
     * This annotation class is used to Social media Login Profile
     */
    annotation class SocialMediaUrl {
        companion object {
            const val http = "http://"
            const val https = "https://"
        }
    }


    /**
     * This annotation class is used to define Bank Account Type
     */
    annotation class AccountType {
        companion object {
            const val CurrentType = "Current Account"
            const val SavingType = "Saving Account"
        }
    }



    /**
     * This annotation class is used for Tutorial screens
     */
    annotation class TutorialScreen {
        companion object {
            const val Welcome = 1
            const val CertifiedCars = 2
            const val EasyWayToFindRide = 3

        }
    }

    /**
     * This annotation class is used for screen types
     */
    annotation class ScreenType {
        companion object {
            const val SendOtp = 0
            const val VerifyOtp = 1
        }
    }


    /**
     * This annotation class is used for Camera Pick Request Code
     */
    annotation class ImageSelectionRequestCode {
        companion object {
            const val CameraRequestCode = 0
            const val GalleryRequestCode = 1
            const val CaptureImageActivityRequestCode = 102
            const val PickImageRequestCode = 101

        }
    }


    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class GoogleLoginCode {
        companion object {
           var RC_SIGN_IN = 110
        }
    }

    /**
     * This annotation class is used for Select Gender
     */
    annotation class Gender {
        companion object {
            var FEMALE = "FEMALE"
            var MALE="MALE"
        }
    }



    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class SelectUnselectServices {
        companion object {
            var isSelectedServicesType = 0
        }
    }

    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class BookingType {
        companion object {
            var isBookingType = -1
            const val BookForLater= "BOOK_FOR_LATER"
            const val BookNow= "BOOK_NOW"
        }
    }



    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class IsEditLocation {
        companion object {
            var isEndEditLocation = 1
            var isStartEditLocation=0

        }
    }



    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class SelectGender {
        companion object {
            var isSelectedGender = -1
            var MALE="MALE"
            var Femail="FEMALE"
            var Noprefrence="NO PREFERENCE"
        }
    }


    /**
     * This annotation class is used for Select EditLocation RequestCode
     */
    annotation class EditLocationRequestCode {
        companion object {
            var editStartDestinationLocationCode = 103
            var editEndDestinationLocationRequestCode=104
            var startDirection=100
            var endDirectionCode=1
        }
    }



    /**
     * This annotation class is used for Select Unselect services Type
     */
    annotation class PlaceAPiKey {
        companion object {
            var PlaceApiKey = "AIzaSyADUeqwiBG-D2JZopmLcvVyYC7C39ERdDY"
        }
    }



    /**
     * This annotation class is used for Select Unselect Trip type
     */
    annotation class SelectTriType {
        companion object {
            var isSelectedTrip = -1
            var privateTrip="PRIVATE"
            var sharedtrip="SHARED"
        }
    }


    /**
     * This annotation class is used for Select Unselect Payment type
     */
    annotation class PaymentType {
        companion object {
            var isSelectedPaymentType = -1
            var prepaid="PREPAID"
            var postpaid="POSTPAID"
        }
    }


    /**
     * This annotation class is used for Select Number of passengers
     */
    annotation class NumberOfPassengers {
        companion object {
            var isSelectNumberOfPassenger = -1
        }
    }


    /**
     * This annotation class is used for Image Type
     */
    annotation class ImageTypes {
        companion object {
            const val ImageType= "image/*"
        }
    }

}