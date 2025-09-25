import PageMeta from "../../components/common/PageMeta";
import CategoryList from "../../components/category/CategoryList";
import PageBreadcrumb from "../../components/common/PageBreadCrumb";
import ComponentCard from "../../components/common/ComponentCard";

export default function Category() {
  return (
    <>
      <PageMeta
        title="React.js SignIn Dashboard | TailAdmin - Next.js Admin Dashboard Template"
        description="This is React.js SignIn Tables Dashboard page for TailAdmin - React.js Tailwind CSS Admin Dashboard Template"
      />
      <PageBreadcrumb pageTitle="Basic Tables" />
      <div className="space-y-6">
        
        <ComponentCard title="Basic Table 1">
          <CategoryList />
        </ComponentCard>
      </div>
          
    </>
  );
}
