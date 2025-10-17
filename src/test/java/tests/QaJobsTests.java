// src/test/java/tests/QaJobsTests.java
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LeverJobPage;
import pages.QaJobsListPage;
import pages.QaLandingPage;

import static org.testng.Assert.assertTrue;

public class QaJobsTests extends BaseTest {

    @Test(description = "QA Landing -> See all QA jobs -> Filtrele -> Kartları doğrula -> View Role (Lever)")
    public void should_filter_qa_jobs_from_landing_and_verify_all() {
        // 1) QA Landing aç + “See all QA jobs”
        QaJobsListPage jobs = new QaLandingPage(driver)
                .open()
                .clickSeeAllQaJobs();


        jobs.filterLocationIstanbulTurkiye();
        jobs.filterDepartmentQA();

        assertTrue(jobs.isJobListVisible());

        LeverJobPage lever = jobs.clickFirstViewRole();
        assertTrue(lever.isOpen(), "Redirect Lever Application sayfasına olmadı!");
        // Yeni sekmede Lever kontrolü (çok basit doğrulama)

    }
}
