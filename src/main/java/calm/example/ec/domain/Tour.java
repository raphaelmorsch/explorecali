package calm.example.ec.domain;

import java.util.Map;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Tour contains all attributes of an Explore California Tour.
 */
@Document
public class Tour {
    @Id
    private String id;

    @Indexed
    private String title;

    private Map<String, String> details;

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Indexed
    private String tourPackageCode;

    private String tourPackageName;

    public String getTourPackageName() {
        return tourPackageName;
    }

    public void setTourPackageName(String tourPackageName) {
        this.tourPackageName = tourPackageName;
    }


    protected Tour() {
    }

    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.details = details;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public void setTourPackageCode(String tourPackageCode) {
        this.tourPackageCode = tourPackageCode;
    }



    @Override
    public String toString() {
        return "Tour [details=" + details + ", id=" + id + ", title=" + title
                + ", tourPackageCode=" + tourPackageCode + ", tourPackageName=" + tourPackageName + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) &&
                Objects.equals(title, tour.title) &&
                Objects.equals(details, tour.details) &&
                Objects.equals(tourPackageCode, tour.tourPackageCode) &&
                Objects.equals(tourPackageName, tour.tourPackageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, details, tourPackageCode, tourPackageName);
    }
}
